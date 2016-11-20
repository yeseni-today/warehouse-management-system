package com.repository.web.login;

import com.repository.base.BaseController;
import com.repository.entity.UsersEntity;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import javax.servlet.http.HttpSession;

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

    @RequestMapping(URL_SIGNIN_SUCCESS)
    public String signinSuccess(HttpSession session, Principal principal) {
        UsersEntity usersEntity = usersDao.findById(principal.getName());
        logger.info("signinSuccess: " + "登陆成功，用户Id:'" + principal.getName() + "';用户名称:'" + usersEntity.getUsersName() + "'");
        session.setAttribute(SESSION_USER, usersEntity);
        return REDIRECT + URL_QUERY;
    }

    @RequestMapping(URL_SIGNIN_FAIL)
    public String signinFail() {
        return REDIRECT + URL_SIGNIN;
    }


}


