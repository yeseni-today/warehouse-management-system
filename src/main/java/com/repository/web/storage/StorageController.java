package com.repository.web.storage;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.SdictionaryDao;
import com.repository.web.storage.add.StorageFormContoller;
import com.repository.web.storage.add.StorageForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.*;

/**
 * Created by Finderlo on 2016/11/6.
 */
@Controller
@RequestMapping(URL_STORAGE)
public class StorageController extends BaseController {

    @Autowired
    ItemInOperationDao inOperationDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    SdictionaryDao sdictionaryDao;

    @RequestMapping
    public String storage(Model model) {
        model.addAttribute("history", inOperationDao.findAll());
        return HTML_STORAGE_HISTORY;
    }


}
