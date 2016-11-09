package com.repertory.web.sotrage;

import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Finderlo on 2016/11/9.
 */
@Controller
@RequestMapping("/storage/add")
public class AddStorageContoller {

    private static final String PREFIX = "tiles/storage/add/";
    @Autowired
    private ItemDao itemDao;

    @RequestMapping("add_item")
    public String addItem() {
        return PREFIX + "add_item";
    }

    @RequestMapping("set_item_info")
    public String setItemInfo(@RequestParam(name = "isAType") boolean isAtype,
                              @RequestParam(name = "itemCode", required = false) String itemCode,
                              Model model) {
        if (isAtype) {
            return PREFIX + "setInfo";
        }

        ItemEntity itemEntity = itemDao.findById(itemCode);

        if (itemEntity != null) {
            model.addAttribute("item", itemEntity);
            return PREFIX + "add/getInfo";
        } else {
            return PREFIX + "setInfo";
        }

    }
}
