package com.repertory.controller;

import com.repertory.bean.UsersEntity;
import com.repertory.dao.UsersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Finderlo on 2016/11/5.
 */
@Controller
public class LoginController {


    @RequestMapping("/")
    public String greeting() {
        return "tiles/query/list";
    }

    @RequestMapping("/signin")
    public String login() {
        return "tiles/login/signin";
    }


    @RequestMapping("/logincheck")
    public String logincheck(@Valid UsersEntity usersEntity, Model model, HttpServletRequest request) {
        UsersDao usersDao = new UsersDao();
        if (usersDao.checkLogin(usersEntity)){
            UsersEntity users = usersDao.findById(usersEntity.getUsersId());
            request.getSession().setAttribute("user",users);
            return "redirect:query";
        }else {
            return "error";
        }
    }


}


