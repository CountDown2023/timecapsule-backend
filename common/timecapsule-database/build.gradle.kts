dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("mysql:mysql-connector-java")

    runtimeOnly("com.h2database:h2")
}
