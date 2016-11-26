package com.repository.web.message;

import com.repository.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.HTML_MESSAGE_LIST;
import static com.repository.Constants.HTML_MESSAGE_NEWMESSAGE;
import static com.repository.Constants.URL_MESSAGE;
import static com.repository.Constants.URL_MESSAGE_NEW;

@Controller
public class MessageController extends BaseController {


    @RequestMapping(URL_MESSAGE)
    public String message() {
        return HTML_MESSAGE_LIST;
    }


    @RequestMapping(URL_MESSAGE_NEW)
    public String newmessage() {
        return HTML_MESSAGE_NEWMESSAGE;
    }
}
