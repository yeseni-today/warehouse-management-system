package com.repository.web.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;

@Controller
public class LogController {

    @RequestMapping(URL_LOG)
    public String log() {
        return HTML_LOG_LOG;
    }
}
