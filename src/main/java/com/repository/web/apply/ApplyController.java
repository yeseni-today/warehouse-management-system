package com.repository.web.apply;

import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apply")
public class ApplyController {

    private static final String PREFIX = "tiles/apply/";

    @Autowired
    ItemApplicationDao applicationDao;

    @Autowired
    ItemApplicationOperationDao applicationOperationDao;

    @RequestMapping(value = {"/", ""})
    public String apply(Model model) {
        model.addAttribute("applications", applicationOperationDao.findAll());
        System.out.println("test");
        return PREFIX + "history";
    }
}
