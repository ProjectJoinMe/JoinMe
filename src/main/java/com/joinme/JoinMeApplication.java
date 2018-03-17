package com.joinme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class JoinMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoinMeApplication.class, args);
    }
}
