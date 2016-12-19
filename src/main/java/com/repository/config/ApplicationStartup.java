package com.repository.config;

import com.repository.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    TimerService _timerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("启动完成");
        new Thread(_timerService).start();
    }
}
