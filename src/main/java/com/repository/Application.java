package com.repository;

import com.repository.config.WebConfig;
import com.repository.dao.AbstractDao;
import com.repository.util.Util;
import com.repository.web.query.QueryController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, Util.class, QueryController.class, AbstractDao.class})
//@Import({WebConfig.class, Factory.class,})
@EnableAutoConfiguration(exclude = {WebConfig.class, SecurityManager.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
