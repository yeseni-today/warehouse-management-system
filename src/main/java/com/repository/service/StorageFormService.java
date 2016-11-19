package com.repository.service;

import com.repository.base.BaseObject;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemCompanyEntity;
import com.repository.entity.ItemEntity;
import com.repository.entity.ItemInOperationEntity;
import com.repository.entity.ItemInStorageEntity;
import com.repository.entity.SdictionaryEntity;
import com.repository.util.Util;
import com.repository.web.storage.add.StorageForm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/11/19.
 */
@Component
@Repository
public class StorageFormService extends BaseObject {
    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemInOperationDao operationDao;
    @Autowired
    ItemInStorageDao storageDao;
    @Autowired
    SdictionaryDao sdictionaryDao;
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(StorageForm storageForm) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        //首先将所有item存入item表中，如果为校内编码需要先查询编码
        List<Item> itemEntities = formToItemEntity(storageForm);

        itemEntities.stream().filter(e -> e.isInSchool()).forEach(e -> {
            SdictionaryEntity sdictionaryEntity = sdictionaryDao.findById(e.getItemCategoryEntity().getCategoryId());
            e.setItemCode(Util.handleCode(sdictionaryEntity));
            sdictionaryEntity.setIndex(sdictionaryEntity.getIndex() + 1);
            session.saveOrUpdate(e.toItemEntity());
            session.saveOrUpdate(sdictionaryEntity);
        });
        itemEntities.stream().filter(e -> !e.isInSchool()).forEach(e -> {
            ItemEntity itemEntity = itemDao.findById(e.getItemCode());
            if (itemEntity != null) {
                itemEntity.setItemCount(itemEntity.getItemCount() + e.getNewItemCount());
                session.saveOrUpdate(itemEntity);
            } else {
                e.setItemCount(e.getNewItemCount());
                session.saveOrUpdate(e.toItemEntity());
            }
        });
        //保存操作表
        ItemInOperationEntity operationEntity = FormToInOpreation(storageForm);
        SdictionaryEntity sdictionaryEntity = sdictionaryDao.findById("storage_ID");
        operationEntity.setStorageId(Util.handleCode(sdictionaryEntity));
        sdictionaryEntity.setIndex(sdictionaryEntity.getIndex() + 1);
        session.saveOrUpdate(operationEntity);
        session.saveOrUpdate(sdictionaryEntity);
        //保存入库表
        List<ItemInStorageEntity> storageEntities = FormToInStorage(storageForm);
        storageEntities.forEach(e -> {
            e.setStorageId(operationEntity.getStorageId());
            for (Item item : itemEntities) {
                if (e.getItemCode().equals(item.getPreItemCode())) {
                    e.setItemCode(item.getItemCode());
                    break;
                }
            }
            session.saveOrUpdate(e);
        });
    }

    public ItemInOperationEntity FormToInOpreation(StorageForm storageForm) {
        ItemInOperationEntity entity = new ItemInOperationEntity();
        entity.setStorageTime(new java.sql.Date(System.currentTimeMillis()));
        entity.setOperationId(storageForm.getOpreationId());
        entity.setStorageId(storageForm.getInStorageId());
        return entity;
    }

    public List<ItemInStorageEntity> FormToInStorage(StorageForm storageForm) {
        List<ItemInStorageEntity> entities = new ArrayList<>();
        storageForm.getItemForms().forEach(itemForm -> {
            ItemInStorageEntity entity = new ItemInStorageEntity();
            entity.setStorageId(storageForm.getInStorageId());
            entity.setPrice(itemForm.getItemPrice());
            entity.setItemIndate(new java.sql.Date(System.currentTimeMillis()));
            entity.setItemCode(itemForm.getItemCode());
            entity.setAllowCount(itemForm.getItemCount());
            entity.setBillCode(itemForm.getBillCode());
            entity.setCounts(itemForm.getItemCount());
            entity.setItemBatch(itemForm.getItemBatch());
            entity.setItemSlot(itemForm.getItemSlot());
            entity.setCounts(itemForm.getItemCount());
            entities.add(entity);
        });
        return entities;
    }

    public List<Item> formToItemEntity(StorageForm storageForm) {
        List<Item> entities = new ArrayList<>();
        storageForm.getItemForms().forEach(itemForm -> {
//            Item entity = (Item) itemDao.findById(itemForm.getItemCode());
            Item entity = Item.fromItemEnity(itemDao.findById(itemForm.getItemCode()));

            if (entity != null) {
                if (itemForm.getItemPrice() != 0) {
                    entity.setItemPrice(itemForm.getItemPrice());
                }
                entity.setNewItemCount(itemForm.getItemCount());
            } else {
                //如果不存在，则设置输入信息信息
                entity = new Item();

                if (itemForm.isInschool()) {
                    entity.setInSchool(true);
                } else entity.setInSchool(false);

                logger.info("itemform name:" + itemForm.getItemName());
                entity.setItemName(itemForm.getItemName());
                ItemCompanyEntity companyEntity = new ItemCompanyEntity();
                entity.setItemCode(itemForm.getItemCode());
                entity.setPreItemCode(itemForm.getItemCode());
                entity.setItemCount(itemForm.getItemCount());

                entity.setItemSpec(itemForm.getItemSpec());
                entity.setItemIntroduce(itemForm.getItemIntroduce());
                companyEntity.setCompanyId(itemForm.getItemCompanyID());
                entity.setItemCategoryId(itemForm.getItemCategoryID());
                entity.setItemCompanyEntity(companyEntity);
                entity.setItemPrice(itemForm.getItemPrice());
                //默认值
                entity.setItemBorrowTimelimit(0);
                entity.setItemState("正常");
                if (itemForm.isInschool()) {
                    entity.setItemExamine("手动");
                } else entity.setItemExamine("自动");
                entity.setItemRemind(0);
            }
            entities.add(entity);
        });
        return entities;
    }

    private static class Item extends ItemEntity {
        private boolean isInSchool;
        private String preItemCode;
        private int newItemCount;

        public boolean isInSchool() {
            return isInSchool;
        }

        public void setInSchool(boolean inSchool) {
            isInSchool = inSchool;
        }

        public String getPreItemCode() {
            return preItemCode;
        }

        public void setPreItemCode(String preItemCode) {
            this.preItemCode = preItemCode;
        }

        public ItemEntity toItemEntity() {
            ItemEntity entity = new ItemEntity();
            entity.setItemName(getItemName());
            entity.setItemPrice(getItemPrice());
            entity.setItemCode(getItemCode());
            entity.setItemSpec(getItemSpec());
            entity.setItemIntroduce(getItemIntroduce());
            entity.setItemCategoryEntity(getItemCategoryEntity());
            entity.setItemCompanyEntity(getItemCompanyEntity());
            entity.setItemPrice(getItemPrice());
            entity.setItemCount(getItemCount());
            //默认值
            entity.setItemBorrowTimelimit(getItemBorrowTimelimit());
            entity.setItemState(getItemState());
            entity.setItemExamine(getItemExamine());
            entity.setItemRemind(getItemRemind());
            return entity;
        }

        public static Item fromItemEnity(ItemEntity itemEntity) {

            if (itemEntity == null) {
                return null;
            }

            Item entity = new Item();

            entity.setItemName(itemEntity.getItemName());
            entity.setItemPrice(itemEntity.getItemPrice());
            entity.setItemCode(itemEntity.getItemCode());
            entity.setItemSpec(itemEntity.getItemSpec());
            entity.setItemIntroduce(itemEntity.getItemIntroduce());
            entity.setItemCategoryEntity(itemEntity.getItemCategoryEntity());
            entity.setItemCompanyEntity(itemEntity.getItemCompanyEntity());
            entity.setItemPrice(itemEntity.getItemPrice());
            entity.setItemCount(itemEntity.getItemCount());
            //默认值
            entity.setItemBorrowTimelimit(itemEntity.getItemBorrowTimelimit());
            entity.setItemState(itemEntity.getItemState());
            entity.setItemExamine(itemEntity.getItemExamine());
            entity.setItemRemind(itemEntity.getItemRemind());
            return entity;
        }

        public int getNewItemCount() {
            return newItemCount;
        }

        public void setNewItemCount(int newItemCount) {
            this.newItemCount = newItemCount;
        }
    }
}
