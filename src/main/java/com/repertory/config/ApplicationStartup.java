package com.repertory.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Finderlo on 2016/11/8.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("启动完成");
    }
}
