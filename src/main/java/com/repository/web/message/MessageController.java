package com.repository.web.message;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/message")
public class MessageController {

    private static final String PREFIX = "tiles/message/";

    @RequestMapping("/")
    public String message() {
        return PREFIX + "list";
    }
}
