package com.repository.web.apply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apply")
public class ApplyController {

    private static final String PREFIX = "tiles/apply/";

    @RequestMapping("/")
    public String apply() {
        return PREFIX + "history";
    }
}
