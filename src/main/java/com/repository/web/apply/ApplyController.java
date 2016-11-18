package com.repository.web.apply;

import com.repository.base.BaseController;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;
@Controller
public class ApplyController extends BaseController {

    @Autowired
    ItemApplicationDao applicationDao;

    @Autowired
    ItemApplicationOperationDao applicationOperationDao;

    @RequestMapping(URL_APPLY)
    public String apply(Model model) {
        logger.trace("apply/");
        model.addAttribute("applications", applicationOperationDao.findAll());
        return HTML_APPLY_HISTORY;
    }
}
