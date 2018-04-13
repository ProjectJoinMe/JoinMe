package com.joinme.frontend.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
/**
 * Created by Nicole, August 2017.
 */
@Configuration
public class FrontendApiPropertySourceConfiguration {

    @Configuration
    @Order(100) // lowest prio
    @PropertySource("classpath:config/spring/frontend-api.default.properties")
    static class Defaults {
    }

    @Configuration
    @Order(99)
    @PropertySource(value = "classpath:config/spring/frontend-api.properties", ignoreResourceNotFound = true)
    static class ClasspathOverrides {
    }

    @Configuration
    @Order(98)
    @PropertySource(value = "config/frontend-api.properties", ignoreResourceNotFound = true)
    static class FileOverrides {
    }
}
