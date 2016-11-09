package com.repertory;

import com.repertory.config.DataSourceConfig;
import com.repertory.config.Factory;
import com.repertory.config.WWebMvcConfig;
import com.repertory.web.QueryController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Finderlo on 2016/11/7.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, QueryController.class,Factory.class})
@Import({WWebMvcConfig.class,Factory.class, DataSourceConfig.class})
@EnableAutoConfiguration

public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
