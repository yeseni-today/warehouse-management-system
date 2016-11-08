package com.repertory.controller;

import com.repertory.dao.ItemInOperationDao;
import com.repertory.dao.ItemInStorageDao;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Finderlo on 2016/11/6.
 */
public class StorageController {

    ItemInOperationDao inOperationDao = new ItemInOperationDao();

    @RequestMapping("/storage")
    public String storage(Model model) {
        model.addAttribute("history",inOperationDao.findAll());
        return "tiles/storage/storage";
    }


}
