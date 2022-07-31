package com.timecapsule.infra.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseProperties {
    private final String username;
    private final String password;
    private final String driverClassName;
    private final String jpaHibernateDdlAuto;
    private final String url;
    private final Integer maximumPoolSize;
    private final Long connectionTimeout;
}
