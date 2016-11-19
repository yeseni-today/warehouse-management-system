//package com.repository.service;
//
//import com.repository.dao.ItemDao;
//import com.repository.dao.ItemInOperationDao;
//import com.repository.dao.ItemInStorageDao;
//import com.repository.dao.SdictionaryDao;
//import com.repository.entity.ItemCompanyEntity;
//import com.repository.entity.ItemEntity;
//import com.repository.entity.ItemInOperationEntity;
//import com.repository.entity.ItemInStorageEntity;
//import com.repository.entity.SdictionaryEntity;
//import com.repository.util.Util;
//import com.repository.web.storage.add.StorageForm;
//
//import org.hibernate.LockMode;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Finderlo on 2016/11/19.
// */
//@Component
//@Repository
//public class StorageFormService {
//    @Autowired
//    ItemDao itemDao;
//    @Autowired
//    ItemInOperationDao operationDao;
//    @Autowired
//    ItemInStorageDao storageDao;
//    @Autowired
//    SdictionaryDao sdictionaryDao;
//    @Autowired
//    SessionFactory sessionFactory;
//
//    public void save(StorageForm storageForm) {
//        Session session = sessionFactory.openSession();
////        session.beginTransaction().;
//        //首先将所有item存入item表中，如果为校内编码需要先查询编码
//        List<ItemEntity> itemEntities = FormToItemEntity(storageForm);
//        itemEntities.stream().filter(e -> e.isInSchool()).forEach(e -> {
//            SdictionaryEntity sdictionaryEntity = sdictionaryDao.findById(e.getItemCategoryEntity().getCategoryId());
//            session.lock(sdictionaryEntity, LockMode.PESSIMISTIC_FORCE_INCREMENT);
//            e.setItemCode(Util.handleCode(sdictionaryEntity));
//            sdictionaryEntity.setIndex(sdictionaryEntity.getIndex() + 1);
//            session.saveOrUpdate(e);
//            session.saveOrUpdate(sdictionaryEntity);
//        });
//        itemEntities.stream().filter(e -> !e.isInSchool()).forEach(e -> {
//            ItemEntity itemEntity = itemDao.findById(e.getItemCode());
//            if (itemEntity != null) {
//                session.lock(itemEntities, LockMode.PESSIMISTIC_FORCE_INCREMENT);
//                itemEntity.setItemCount(itemEntity.getItemCount() + e.getItemCount());
//                session.saveOrUpdate(itemEntity);
//            } else {
//                session.saveOrUpdate(e);
//            }
//        });
//        //保存操作表
//        ItemInOperationEntity operationEntity = FormToInOpreation(storageForm);
//        SdictionaryEntity sdictionaryEntity = sdictionaryDao.findById("storage_ID");
//        session.lock(sdictionaryEntity, LockMode.PESSIMISTIC_FORCE_INCREMENT);
//        operationEntity.setStorageId(Util.handleCode(sdictionaryEntity));
//        sdictionaryEntity.setIndex(sdictionaryEntity.getIndex() + 1);
//        session.saveOrUpdate(operationEntity);
//        session.saveOrUpdate(sdictionaryEntity);
//        //保存入库表
//        List<ItemInStorageEntity> storageEntities = FormToInStorage(storageForm);
//        storageEntities.forEach(e -> {
//            e.setStorageId(operationEntity.getStorageId());
//        });
//    }
//
//    public ItemInOperationEntity FormToInOpreation(StorageForm storageForm) {
//        ItemInOperationEntity entity = new ItemInOperationEntity();
//        entity.setStorageTime(new java.sql.Date(System.currentTimeMillis()));
//        entity.setOperationId(storageForm.getOpreationId());
//        entity.setStorageId(storageForm.getInStorageId());
//        return entity;
//    }
//
//    public List<ItemInStorageEntity> FormToInStorage(StorageForm storageForm) {
//        List<ItemInStorageEntity> entities = new ArrayList<>();
//        storageForm.getItemForms().forEach(itemForm -> {
//            ItemInStorageEntity entity = new ItemInStorageEntity();
//            entity.setStorageId(storageForm.getInStorageId());
//            entity.setPrice(itemForm.getItemPrice());
//            entity.setItemIndate(new java.sql.Date(System.currentTimeMillis()));
//            entity.setItemCode(itemForm.getItemCode());
//            entity.setAllowCount(itemForm.getItemCount());
//            entity.setBillCode(itemForm.getBillCode());
//            entity.setCounts(itemForm.getItemCount());
//            entity.setItemBatch(itemForm.getItemBatch());
//            entity.setItemSlot(itemForm.getItemSlot());
//            entity.setCounts(itemForm.getItemCount());
//            entities.add(entity);
//        });
//        return entities;
//    }
//
//    public List<ItemEntity> FormToItemEntity(StorageForm storageForm) {
//        List<ItemEntity> entities = new ArrayList<>();
//        storageForm.getItemForms().forEach(itemForm -> {
//
//
//            ItemEntity entity = itemDao.findById(itemForm.getItemCode());
//            if (entity != null) {
//                //如果数据库中存在，则只修改数量和价格（如果价格改变）
////                entity.setItemCount(itemForm.getItemCount() + entity.getItemCount());
//                if (itemForm.getItemPrice() != 0) {
//                    entity.setItemPrice(itemForm.getItemPrice());
//                }
//            } else {
//                //如果不存在，则设置输入信息信息
//                entity = new ItemEntity();
//
//                if (itemForm.isInschool()) {
//                    entity.setInSchool(true);
//                } else entity.setInSchool(false);
//
//                ItemCompanyEntity companyEntity = new ItemCompanyEntity();
//                entity.setItemCode(itemForm.getItemCode());
//                entity.setItemSpec(itemForm.getItemSpec());
//                entity.setItemIntroduce(itemForm.getItemIntroduce());
//                companyEntity.setCompanyId(itemForm.getItemCompanyID());
//                entity.setItemCompanyEntity(companyEntity);
//                entity.setItemPrice(itemForm.getItemPrice());
//                //默认值
//                entity.setItemBorrowTimelimit(0);
//                entity.setItemState("正常");
//                if (itemForm.isInschool()) {
//                    entity.setItemExamine("手动");
//                } else entity.setItemExamine("自动");
//                entity.setItemRemind(0);
//            }
//            entities.add(entity);
//        });
//        return entities;
//    }
//}
