package com.repertory.cotroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Finderlo on 2016/10/24.
 */
@SpringBootApplication
@Controller
public class Application {

    @RequestMapping("/")
    String greeting() {
        return "login";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
    @RequestMapping("/logincheck")
    String logincheck(){
        
        return "user_admin";
    }

}
