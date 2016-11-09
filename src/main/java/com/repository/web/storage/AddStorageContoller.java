package com.repository.web.storage;

import com.repository.entity.ItemEntity;
import com.repository.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Finderlo on 2016/11/9.
 */
@Controller
@RequestMapping("/storage/add")
public class AddStorageContoller {

    private static final String PREFIX = "tiles/storage/add/";

    public static final String STORAGE_FORM = "storageForm";
    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "/add_item", method = RequestMethod.GET)
    public String addItem() {
        return PREFIX + "add_item";
    }

    @RequestMapping("/set_item_info")
    public String setItemInfo(@RequestParam(name = "itemCode", required = false) String itemCode,
                              Model model) {
        ItemEntity itemEntity = itemDao.findById(itemCode);
        ItemInfo itemInfo = new ItemInfo(itemEntity);
        model.addAttribute("item_info", itemInfo);
        System.out.println(itemInfo.entries.size());
        itemInfo.entries.forEach(entry -> {
            System.out.println(entry.getKey_cn());
            System.out.println(entry.getKey_en());
            System.out.println(entry.getValue());
        });
        return PREFIX + "setInfo11";

    }

    @RequestMapping("/add_itemInStorage")
    public String addItemIn(ItemEntity itemEntity, HttpSession session) {
        System.out.println(itemEntity.getItemName());
        return "tiles/storage/new_storage_form";
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    public String addItemTosession(ItemForm itemForm, HttpSession session) {
        StorageForm storageForm = (StorageForm) session.getAttribute(STORAGE_FORM);
        if (storageForm == null) {
            storageForm = new StorageForm();
        }
        storageForm.getItemForms().add(itemForm);
        session.setAttribute(STORAGE_FORM, storageForm);
        return "tiles/storage/new_storage_form";
    }



}
