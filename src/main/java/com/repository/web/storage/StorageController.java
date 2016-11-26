package com.repository.web.storage;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemInOperationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.repository.Constants.DAY_1_MILLIS;
import static com.repository.Constants.HTML_STORAGE_HISTORY;
import static com.repository.Constants.TILES_PREFIX;
import static com.repository.Constants.URL_STORAGE;

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

        logger.info("storage: date:" + System.currentTimeMillis());
        List<ItemInOperationEntity> result =
                inOperationDao.findAll()
                        .stream()
                        .filter(entity -> ((System.currentTimeMillis() - entity.getStorageTime().getTime()) / 30) < DAY_1_MILLIS)
                        .collect(Collectors.toList());

        logger.info("storage: listsize:" + result.size());
        model.addAttribute("history", result);
        return TILES_PREFIX + HTML_STORAGE_HISTORY;
    }


}
