package com.repository;

import com.repository.config.DataSourceConfig;
import com.repository.config.Factory;
import com.repository.config.WebConfig;
import com.repository.web.QueryController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Finderlo on 2016/11/7.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, QueryController.class, Factory.class})
@Import({WebConfig.class, Factory.class, DataSourceConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceConfig.class, WebConfig.class, SecurityManager.class})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
