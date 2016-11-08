package com.repertory.controller;

import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemDao;
import com.repertory.dao.ItemRespository;

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

/**
 * Created by Finderlo on 2016/11/5.
 */
@Controller
public class QueryController {

    ItemDao itemDao = new ItemDao();

//    @Autowired
//    ItemRespository itemRespository;

    @RequestMapping("/queryItem")
    public String queryItem(@RequestParam(name = "itemCode",required = false) String itemCode,
                            @RequestParam(name = "itemName ",required = false) String itemName,
                            @RequestParam(name = "itemCategoryId",required = false) String itemCategoryId,
                            Model model) {

        List<ItemEntity> result = new ArrayList<>();
        if (itemCode != null && !itemCode.trim().equals("")) {
            ItemEntity itemEntity = itemDao.findById(itemCode);
            if (itemEntity != null) {
                result.add(itemEntity);
            }
        }
        List<ItemEntity> itemEntities = itemDao.query(new String[]{"itemName","itemCategoryId"}
                , new String[]{itemName,itemCategoryId}, true);
        for (ItemEntity entity : itemEntities) {
            result.add(entity);
        }
        model.addAttribute("items", result);
        return "tiles/query/list";
    }

    @RequestMapping("/query")
    public String queryTo(Model model) {
        model.addAttribute("items",itemDao.findAll());
//        model.addAttribute("items",itemRespository.findAll());
        return "tiles/query/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
