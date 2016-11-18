package com.repository.web.log;

import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;

public class LogController {

    @RequestMapping(URL_LOG)
    public String log() {
        return HTML_LOG_HISTORY;
    }
}
