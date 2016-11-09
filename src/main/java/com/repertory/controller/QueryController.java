package com.repertory.controller;

import com.repertory.bean.ItemCategoryEntity;
import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemCategoryDao;
import com.repertory.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class QueryController {

    //    ItemDao itemDao = new ItemDao();
    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemCategoryDao categoryDao;
//    ItemCategoryDao categoryDao = new ItemCategoryDao();


    @RequestMapping("/queryItem")
    public String queryItem(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                            Model model) {
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        return "tiles/query/list";
    }

    @RequestMapping("/query")
    public String queryTo(Model model) {
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute("categories", categories());
        return "tiles/query/list";
    }

    public List<ItemCategoryEntity> categories() {
        return categoryDao.findAll();
    }

}
