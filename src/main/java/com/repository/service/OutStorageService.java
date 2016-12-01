package com.repository.service;

import com.repository.dao.ItemCategoryDao;
import com.repository.dao.ItemCompanyDao;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemApplicationEntity;
import com.repository.entity.ItemApplicationOperationEntity;
import com.repository.entity.ItemEntity;
import com.repository.entity.ItemOutOperationEntity;
import com.repository.entity.ItemOutStorageEntity;
import com.repository.entity.SdictionaryEntity;
import com.repository.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/12/1.
 */
@Component
public class OutStorageService {

    @Autowired
    SdictionaryDao sdictionaryDao;

    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemInOperationDao operationDao;
    @Autowired
    ItemInStorageDao storageDao;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    LogSerivce logSerivce;
    @Autowired
    ItemCompanyDao companyDao;
    @Autowired
    ItemCategoryDao categoryDao;
    @Autowired
    OutStorageService outStorageService;

    @Transactional
    public void saveAutoStoage(ItemApplicationOperationEntity operationEntities, List<ItemApplicationEntity> items){
        ItemOutOperationEntity outOperationEntity = toOutOprea(operationEntities);
        Session session = sessionFactory.getCurrentSession();
        //保存入库操作表
        SdictionaryEntity applicationIdEntity = sdictionaryDao.findById("out_ID");
        outOperationEntity.setOutId(String.valueOf(Util.handleCode(applicationIdEntity)));
        applicationIdEntity.setIndex(applicationIdEntity.getIndex() + 1);
        session.update(applicationIdEntity);
        session.save(outOperationEntity);

        //保存出库表
        List<ItemOutStorageEntity> outStorageEntities = toOutStorage(items,outOperationEntity.getOutId());
        outStorageEntities.forEach(out -> {
            session.save(out);
            ItemEntity itemEntity = itemDao.findById(out.getItemCode());
            itemEntity.setItemCount(itemEntity.getItemCount()-out.getCounts());
            session.update(itemEntity);
        });

        //修改item表的数量
    }

    public ItemOutOperationEntity toOutOprea(ItemApplicationOperationEntity operationEntities){
        ItemOutOperationEntity outStorageEntity = new ItemOutOperationEntity();
        outStorageEntity.setUsersId(operationEntities.getUsersId());
        outStorageEntity.setApplyId(operationEntities.getApplicationId());
        outStorageEntity.setOperationId("10000");
        outStorageEntity.setOutAddress("测试");
        outStorageEntity.setOutStates("测试：出库正在完成");
        outStorageEntity.setOutTime(new Date(System.currentTimeMillis()));
        outStorageEntity.setOutId(sdictionaryDao.getOutStorageId());
        return outStorageEntity;
    }

    public List<ItemOutStorageEntity> toOutStorage(List<ItemApplicationEntity> itemApplicationEntityList,String outId){
        List<ItemOutStorageEntity> result = new ArrayList<>();
        for (ItemApplicationEntity applicationEntity : itemApplicationEntityList) {
            ItemOutStorageEntity storageEntity = new ItemOutStorageEntity();
            storageEntity.setOutId(outId);
            storageEntity.setCounts(applicationEntity.getCounts());
            storageEntity.setItemCode(applicationEntity.getItemCode());
            result.add(storageEntity);
        }
        return result;
    }
    //出库操作表（出库单编号，对应申请(借取)单编号，出库日期，领取人ID，领取地点，出库状态，操作人员ID）
}
