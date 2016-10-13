package com.joinme.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Ich will nach Hause!";
    }

    @RequestMapping("/{number}")
    @ResponseBody
    public String testPathVariable(@PathVariable("number") int number) {
        return "Hello du bist nummer: " + number;
    }
}
