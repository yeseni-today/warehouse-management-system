package com.repertory.controller;

import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemDao;
import com.repertory.dao.ItemInOperationDao;
import com.repertory.dao.ItemInStorageDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Finderlo on 2016/11/6.
 */
@Controller
@RequestMapping("/storage")
public class StorageController {

    private static final String PREFIX = "tiles/storage/";

    ItemInOperationDao inOperationDao = new ItemInOperationDao();

    @Autowired
    ItemDao itemDao;

    @RequestMapping
    public String storage(Model model) {
        model.addAttribute("history", inOperationDao.findAll());
        return PREFIX + "history";
    }

    @RequestMapping("new_storage_form")
    public String newStorageForm() {
        return PREFIX + "new_storage_form";
    }

    @RequestMapping("add/add_item")
    public String addItem() {
        return PREFIX + "add/add_item";
    }

    @RequestMapping("add/set_item_info")
    public String setItemInfo(@RequestParam(name = "isAType") boolean isAtype,
                              @RequestParam(name = "itemCode", required = false) String itemCode,
                              Model model) {
        if (isAtype) {
            return PREFIX + "add/setInfo";
        }

        ItemEntity itemEntity = itemDao.findById(itemCode);

        if (itemEntity!=null){
            model.addAttribute("item",itemEntity);
            return PREFIX + "add/getInfo";
        }else {
            return PREFIX + "add/setInfo";
        }

    }


}
