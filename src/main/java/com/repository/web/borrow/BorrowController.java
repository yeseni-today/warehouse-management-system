package com.repository.web.borrow;

import com.repository.base.BaseController;
import com.repository.dao.ItemBorrowOpreationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;
@Controller
@RequestMapping(URL_BORROW)
public class BorrowController extends BaseController {

    @Autowired
    ItemBorrowOpreationDao borrowOpreationDao;

    @RequestMapping({"/", ""})
    public String borrow(Model model) {
        model.addAttribute("borrowOpreations", borrowOpreationDao.findAll());
        return HTML_BORROW_HISTORY;
    }
}
