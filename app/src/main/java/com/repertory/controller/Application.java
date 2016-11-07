package com.repertory.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Finderlo on 2016/11/7.
 */
@SpringBootApplication
@Controller
public class Application {

    @RequestMapping("/")
    String greeting() {
    return "login";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
