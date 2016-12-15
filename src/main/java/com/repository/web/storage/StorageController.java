package com.repository.web.storage;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.DictionaryDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.entity.ItemInOperationEntity;

import com.repository.entity.ItemInStorageEntity;
import com.repository.model.SimpleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.repository.common.Constants.*;

/**
 * Created by Finderlo on 2016/11/6.
 */
@Controller
public class StorageController extends BaseController {


    @Autowired
    ItemInOperationDao inOperationDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    DictionaryDao dictionaryDao;

    @Autowired
    ItemInStorageDao itemInStorageDao;

    /**
     * 入库单历史
     *
     * @return html view
     */
    @RequestMapping(URL_STORAGE)
    public String storage(Model model) {

        logger.info("storage: date:" + System.currentTimeMillis());
        List<ItemInOperationEntity> result =
                inOperationDao.findAll()
                        .stream()
                        //30天内
                        .filter(entity -> ((System.currentTimeMillis() - entity.getStorageTime().getTime()) / 30) < DAY_1_MILLIS)
                        .collect(Collectors.toList());

        reverse(result);
        logger.info("storage: listsize:" + result.size());
        model.addAttribute("history", result);
        return TILES_PREFIX + HTML_STORAGE_HISTORY;
    }

    @RequestMapping(URL_STORAGE_QUERY_INFO)
    @ResponseBody
    public SimpleRes query(@RequestParam("storage_id") String storage_id) {
        if (storage_id == null || storage_id.trim().equals(""))
            return SimpleRes.error();
        return SimpleRes.success(
                new Storage(
                        inOperationDao.findById(storage_id),
                        itemInStorageDao.query("storageId", storage_id, false)));
    }

    static class Storage extends ItemInOperationEntity {
        private String storageId;
        private Date storageTime;
        private String operationId;
        List<ItemInStorageEntity> items;

        public Storage(ItemInOperationEntity operationEntity, List<ItemInStorageEntity> items) {
            this.operationId = operationEntity.getOperationId();
            this.items = items;
            this.storageId = operationEntity.getStorageId();
            this.storageTime = operationEntity.getStorageTime();
        }

        @Override
        public String getStorageId() {
            return storageId;
        }

        @Override
        public void setStorageId(String storageId) {
            this.storageId = storageId;
        }
    }

    /**
     * 获取入库单列表，
     *
     * @param day 最近多少天的列表
     * @return SimpleRes
     */
    @RequestMapping(URL_STORAGE_LIST_AJAX)
    @ResponseBody
    public SimpleRes storageDayBefore(@RequestParam(name = "day") int day) {
        // TODO: 2016/12/9 参数比较多，只写了最近实践的参数
        List<ItemInOperationEntity> entities = inOperationDao.findByDayBefore(day);
        reverse(entities);
        return SimpleRes.success(entities);
    }

    public void reverse(List<ItemInOperationEntity> list) {
        //先排序
        list.sort(Comparator.comparingLong(e -> e.getStorageTime().getTime()));
        //降序排列
        Collections.reverse(list);
    }


}
