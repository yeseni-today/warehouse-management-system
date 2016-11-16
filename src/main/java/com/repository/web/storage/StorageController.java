package com.repository.web.storage;

import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.web.storage.add.StorageFormContoller;
import com.repository.web.storage.add.StorageForm;

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

    private static final String HTML_STORAGE_HISTORY = PREFIX + "history";

    ItemInOperationDao inOperationDao = new ItemInOperationDao();

    @Autowired
    ItemDao itemDao;

    @RequestMapping
    public String storage(Model model) {
        model.addAttribute("history", inOperationDao.findAll());
        return HTML_STORAGE_HISTORY;
    }

    @RequestMapping("new_storage_form")
    public String newStorageForm(HttpSession session) {
        StorageForm storageForm = (StorageForm) session.getAttribute(StorageFormContoller.STORAGE_FORM);
        if (storageForm == null) {
            storageForm = new StorageForm();
        }
        session.setAttribute(StorageFormContoller.STORAGE_FORM, storageForm);
        return PREFIX + "new_storage_form";
    }



}
