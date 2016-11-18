package com.repository;

import com.repository.config.WebConfig;
import com.repository.dao.AbstractDao;
import com.repository.util.Util;
import com.repository.web.query.QueryController;

import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, Util.class, QueryController.class, AbstractDao.class})
@EnableAutoConfiguration(exclude = {WebConfig.class, SecurityManager.class})
//@ActiveProfiles("lx")
@ActiveProfiles("default")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
