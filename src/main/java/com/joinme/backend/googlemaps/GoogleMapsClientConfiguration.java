package com.joinme.backend.googlemaps;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Created by Nicole, August 2017.
 */
@Configuration
public class GoogleMapsClientConfiguration {

    /**
     * Redeems API key
     * @return
     */
    @Bean
    GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyBFSDX0w4PcRM536-oUFTl7TTTOqSASy70")
                .build();
    }
}
