package com.joinme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Component
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registerResourceHandler(registry, "/js/**", "classpath:/static/javascript/", "classpath:/static/transpiled-ts/");
        registerResourceHandler(registry, "/css/**", "classpath:/static/css/");
        registerResourceHandler(registry, "/*.html", "classpath:/static/html/");
    }

    private void registerResourceHandler(ResourceHandlerRegistry registry, String pathPattern, String... resourceLocations) {
        registry.addResourceHandler(pathPattern).addResourceLocations(resourceLocations);
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/static/html/");
//        resolver.setSuffix(".html");
        return resolver;
    }
}
