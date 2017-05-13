package com.repository;

import com.repository.config.WebConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class Application {
    //lx5548
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
