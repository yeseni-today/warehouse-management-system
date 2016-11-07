package com.repertory;

import com.repertory.config.Factory;
import com.repertory.config.WWebMvcConfig;
import com.repertory.controller.LoginController;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * Created by Finderlo on 2016/11/7.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, LoginController.class,Factory.class})
@Import({WWebMvcConfig.class,Factory.class})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
