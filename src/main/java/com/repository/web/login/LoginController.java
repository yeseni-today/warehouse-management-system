package com.repository.web.login;

import com.repository.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;
/**
 * Created by Finderlo on 2016/11/5.
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping(URL_INDEX)
    public String greeting() {
        return HTML_QUERY_LIST;
    }

    @RequestMapping(SIGNIN)
    public String login() {
        return HTML_LOGIN_SIGNIN;
    }



}


