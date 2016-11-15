package com.repository.web.storage.instroage;

import com.repository.entity.ItemEntity;
import com.repository.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Finderlo on 2016/11/9.
 */
@Controller
@RequestMapping("/storage/add")
public class StorageFormContoller {

    private static final String PREFIX = "tiles/storage/add/";
    public static final String STORAGE_FORM = "storageForm";
    @Autowired
    private ItemDao itemDao;

    @ModelAttribute
    public void storageForm(Model model, HttpSession session) {
        StorageFormBean storageFormBean = (StorageFormBean) session.getAttribute(STORAGE_FORM);
        if (storageFormBean == null) {
            session.setAttribute(STORAGE_FORM, new StorageFormBean());
        }
    }

    @RequestMapping("/storage_from")
    public String addNew() {
        return PREFIX + "storage_form";
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.GET)
    public String addItem() {
        return PREFIX + "add_item";
    }


    @RequestMapping(value = "/additem", method = RequestMethod.GET)
    public String addItemTosession(ItemForm itemForm,
                                   @RequestParam(name = "itemCode") String itemCode, HttpSession session) {

        StorageFormBean storageForm = (StorageFormBean) session.getAttribute(STORAGE_FORM);
        storageForm.getItemForms().add(itemForm);
        session.setAttribute(STORAGE_FORM, storageForm);
        return "tiles/storage/add/storage_form";
    }

//    @RequestMapping("/additem")
//    public String addItemIn(ItemForm itemForm, HttpSession session) {
//        StorageFormBean storageFormBean = (StorageFormBean) session.getAttribute(STORAGE_FORM);
//        storageFormBean.getItemForms().add(itemForm);
//        return "tiles/storage/add/storage_form";
//    }


    @RequestMapping("/set_item_info")
    public String setItemInfo(
//            @RequestParam(name = "hasCode") boolean hasCode,
            @RequestParam(name = "itemCode", required = false) String itemCode,
            Model model) {
        ItemEntity itemEntity = itemDao.findById(itemCode);
        ItemInfo itemInfo = new ItemInfo(itemEntity);
        model.addAttribute("item_info", itemInfo);
        return PREFIX + "setInfo11";

    }

}
