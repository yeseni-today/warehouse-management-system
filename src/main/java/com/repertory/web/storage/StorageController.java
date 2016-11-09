package com.repertory.web.storage;

import com.repertory.dao.ItemDao;
import com.repertory.dao.ItemInOperationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
    public String newStorageForm(HttpSession session) {
        StorageForm storageForm = (StorageForm) session.getAttribute(AddStorageContoller.STORAGE_FORM);
        if (storageForm == null) {
            storageForm = new StorageForm();
        }
        session.setAttribute(AddStorageContoller.STORAGE_FORM, storageForm);
        return PREFIX + "new_storage_form";
    }



}
