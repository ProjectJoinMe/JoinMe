package com.joinme.frontend.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.TimeUnit;

@Component
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${joinme.frontend-api.cache-control.enabled}")
    private boolean caching;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registerResourceHandler(registry, "", "classpath:/static/webpack/index.html");
        registerResourceHandler(registry, "/", "classpath:/static/webpack/index.html");

        registerResourceHandler(registry, "/**", "classpath:/static/webpack/");
        registerResourceHandler(registry, "/assets/**", "classpath:/static/assets/");
    }

    private void registerResourceHandler(ResourceHandlerRegistry registry, String pathPattern, String... resourceLocations) {
        ResourceHandlerRegistration registration = registry
                .addResourceHandler(pathPattern)
                .addResourceLocations(resourceLocations);
        if (caching) {
            registration.setCacheControl(
                    CacheControl
                            .maxAge(1, TimeUnit.DAYS)
                            .cachePublic()
            );
        }
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/static/webpack/");
        return resolver;
    }
}
