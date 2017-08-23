package com.joinme.backend.googlemaps;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsClientConfiguration {

    @Bean
    GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyBFSDX0w4PcRM536-oUFTl7TTTOqSASy70")
                .build();
    }
}
