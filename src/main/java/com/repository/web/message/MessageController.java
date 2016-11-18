package com.repository.web.message;

import com.repository.base.BaseController;

import org.springframework.context.annotation.Import;

import static com.repository.Constants.*;

import org.springframework.web.bind.annotation.RequestMapping;


public class MessageController extends BaseController {


    @RequestMapping(URL_MESSAGE)
    public String message() {
        return HTML_MESSAGE_LIST;
    }
}
