package com.joinme.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Configuration
public class DatabasePropertySourceConfiguration {
    // see http://stackoverflow.com/questions/12691812/can-propertysources-be-chosen-by-spring-profile

    @Configuration
    @Order(100) // lowest prio
    @PropertySource("classpath:config/spring/database.default.properties")
    static class Defaults {
    }

    @Configuration
    @Order(99)
    @PropertySource(value = "classpath:config/spring/database.properties", ignoreResourceNotFound = true)
    static class ClasspathOverrides {
    }

    @Configuration
    @Order(98)
    @PropertySource(value = "config/database.properties", ignoreResourceNotFound = true)
    static class FileOverrides {
    }

    @Configuration
    @Order(97)
    @Profile("mysql-local-dev")
    @PropertySource("classpath:dev-config/mysql-local.properties")
    static class MySqlLocalDevConfiguration {
    }
}
