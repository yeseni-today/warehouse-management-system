package com.repository.web.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Finderlo on 2016/12/7.
 */
@Controller
public class Test {

    @RequestMapping("/test")
    public String vo(){
        return "test";
    }

}
