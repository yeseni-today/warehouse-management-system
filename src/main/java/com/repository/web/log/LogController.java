package com.repository.web.log;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/log")
public class LogController {
    private static final String PREFIX = "tiles/log/";

    @RequestMapping("/")
    public String log() {
        return PREFIX + "history";
    }
}
