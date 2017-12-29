package com.joinme;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class JoinMeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JoinMeApplication.class, args);
    }
}
