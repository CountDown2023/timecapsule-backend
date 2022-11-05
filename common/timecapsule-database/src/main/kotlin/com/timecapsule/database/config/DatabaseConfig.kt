package com.timecapsule.database.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.SpringBeanContainer
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.timecapsule.database"],
    transactionManagerRef = "transactionManager",
    entityManagerFactoryRef = "entityManager",
)
@EntityScan("com.timecapsule.database")
@EnableConfigurationProperties(DatabaseProperties::class)
@Configuration
class DatabaseConfig(
    private val databaseProperties: DatabaseProperties
) {

    @Bean
    fun dataSource(): DataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = databaseProperties.url
                username = databaseProperties.username
                password = databaseProperties.password
                driverClassName = databaseProperties.driverClassName
                maximumPoolSize = databaseProperties.maximumPoolSize
                connectionTimeout = databaseProperties.connectionTimeout
                isReadOnly = false
            }
        )

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager =
        JpaTransactionManager().apply {
            this.entityManagerFactory = entityManagerFactory.getObject()
        }

    @Bean
    fun entityManagerFactory(
        @Qualifier("dataSource") dataSource: DataSource,
        beanFactory: ConfigurableListableBeanFactory
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            this.setPackagesToScan("com.timecapsule.database")
            this.jpaVendorAdapter = HibernateJpaVendorAdapter()
            this.setJpaPropertyMap(
                mapOf(
                    "hibernate.hbm2ddl.auto" to databaseProperties.jpaHibernateDdlAuto,
                    "hibernate.implicit_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                    "hibernate.physical_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                    AvailableSettings.BEAN_CONTAINER to SpringBeanContainer(beanFactory)
                )
            )
        }
}
