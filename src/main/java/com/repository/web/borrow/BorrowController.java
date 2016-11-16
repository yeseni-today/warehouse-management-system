package com.repository.web.borrow;

import com.repository.dao.ItemBorrowDao;
import com.repository.dao.ItemBorrowOpreationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    private static final String PREFIX = "tiles/borrow/";
    public static final String HTML_BORROW_HISTORY = PREFIX + "history";

    @Autowired
    ItemBorrowOpreationDao borrowOpreationDao;

    @RequestMapping({"/", ""})
    public String borrow(Model model) {
        model.addAttribute("borrowOpreations", borrowOpreationDao.findAll());
        return HTML_BORROW_HISTORY;
    }
}
