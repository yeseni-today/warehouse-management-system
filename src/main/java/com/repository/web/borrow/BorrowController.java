package com.repository.web.borrow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    private static final String PREFIX = "tiles/borrow/";

    @RequestMapping("/")
    public String borrow() {
        return PREFIX + "history";
    }
}
