package com.repository.web.sign;

import com.repository.Constants;
import com.repository.base.BaseController;
import com.repository.entity.UsersEntity;
import com.repository.dao.UsersDao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @RequestMapping(URL_SIGNIN)
    public String login() {
        return HTML_LOGIN_SIGNIN;
    }



}


