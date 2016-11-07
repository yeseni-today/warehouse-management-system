package com.repertory.controller;

import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemDao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/5.
 */
@Controller
public class QueryController {

//    @RequestMapping("/queryItem.do")
    public String queryItem(@RequestParam(name = "itemCode") String itemCode,
                                  @RequestParam(name = "itemName ") String itemName,
                                  @RequestParam(name = "itemCategoryId") String itemCategoryId,
                            Model model
                                  ) {
        List<ItemEntity> result = new ArrayList<>();
        ItemDao itemDao = new ItemDao();

        if (itemCode != null && !itemCode.trim().equals("")) {
            ItemEntity itemEntity = itemDao.queryById(itemCode);
            if (itemEntity != null) {
                result.add(itemEntity);
            }
        }
        List<ItemEntity> itemEntities = itemDao.query(new String[]{"itemName", "itemCategory"}, new String[]{itemName, itemCategoryId}, true);
        for (ItemEntity entity : itemEntities) {
            result.add(entity);
        }

        model.addAttribute("items",result);
        return "user_admin";
    }
    @RequestMapping("/queryItem.do")
    public String query(Model model) {
        ItemDao itemDao = new ItemDao();
        model.addAttribute("items", itemDao.query(null, null));
        System.out.println("queryItem.do");
        return "query";
    }

    @RequestMapping("/queryItem.to")
    public String queryTo(Model model) {
        ItemDao itemDao = new ItemDao();
        model.addAttribute("items", itemDao.query(null, null));
        System.out.println("queryItem.do");
        return "query";
    }
}
