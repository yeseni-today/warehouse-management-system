package com.repository.web.apply;

import com.repository.base.BaseController;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;
import com.repository.entity.ItemApplicationOperationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        List<ItemApplicationOperationEntity> entities = applicationOperationDao.findAll();
        model.addAttribute("history", entities);
        logger.info("apply: applications size:" + entities.size());
        entities.forEach(entity -> logger.info("apply: id:" + entity.getApplicationId()));
        return TILES_PREFIX + HTML_APPLY_HISTORY;
    }

    @RequestMapping(URL_APPLY_AJAX)
    public String applyajax(Model model) {
        logger.trace("apply/");
        List<ItemApplicationOperationEntity> entities = applicationOperationDao.findAll();
        model.addAttribute("history", entities);
        logger.info("apply: applications size:" + entities.size());
        entities.forEach(entity -> logger.info("apply: id:" + entity.getApplicationId()));
        return HTML_APPLY_HISTORY.concat(" ::content");
    }
}
