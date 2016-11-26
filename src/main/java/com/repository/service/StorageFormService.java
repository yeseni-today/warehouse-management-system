package com.repository.service;

import com.repository.base.BaseObject;
import com.repository.dao.ItemCategoryDao;
import com.repository.dao.ItemCompanyDao;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemCategoryEntity;
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
import java.util.stream.Collectors;

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
        //保存入库表
        List<ItemInStorageEntity> storageEntities = FormToInStorage(storageForm);
        //首先将所有item存入item表中，如果为校内编码需要先查询编码
        List<Item> itemEntities = formToItemEntity(storageForm);

        itemEntities.stream().filter(e -> e.isInSchool()).forEach(e -> {
            SdictionaryEntity sdictionaryEntity = sdictionaryDao.findById(e.getItemCategoryEntity().getCategoryId());
            e.setItemCode(Util.handleCode(sdictionaryEntity));

            List temp = storageEntities.stream().filter(storageEntity -> storageEntity.getItemCode().equals(e.getPreItemCode())).collect(Collectors.toList());
            if (temp != null && !temp.isEmpty()) {
                ((ItemInStorageEntity) temp.get(0)).setItemCode(e.getItemCode());
            }

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
                logger.info("isInschool:" + e.isInSchool);
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
            if (itemForm.isInschool()) {
                for (int i = 0; i < itemForm.getItemCount(); i++) {
                    ItemInStorageEntity entity1 = entity.clone();
                    entity1.setCounts(1);
                    entities.add(entity1);
                }
            } else {
                entities.add(entity);
            }
        });
        return entities;
    }

    @Autowired
    ItemCompanyDao companyDao;
    @Autowired
    ItemCategoryDao categoryDao;

    /**
     * 2016/11/26 校内编码是一物一码,如果一个校内物品有多个数量，则再次遍历生成对象
     **/
    public List<Item> formToItemEntity(StorageForm storageForm) {
        List<Item> entities = new ArrayList<>();
        storageForm.getItemForms().forEach(itemForm -> {
            Item entity = Item.fromItemEnity(itemDao.findById(itemForm.getItemCode()));

            if (entity != null) {
                if (itemForm.getItemPrice() != 0) {
                    entity.setItemPrice(itemForm.getItemPrice());
                }
                entity.setNewItemCount(itemForm.getItemCount());
            } else {
                //如果不存在，则设置输入信息信息
                entity = new Item();

                entity.setInSchool(false);

                logger.info("itemform name:" + itemForm.getItemName());
                entity.setItemName(itemForm.getItemName());
                ItemCompanyEntity companyEntity = companyDao.findById(itemForm.getItemCompanyID());
                ItemCategoryEntity catogoryEntity = categoryDao.findById(itemForm.getItemCategoryID());
                entity.setItemCode(itemForm.getItemCode());
                entity.setPreItemCode(itemForm.getItemCode());
                entity.setItemCount(itemForm.getItemCount());

                entity.setItemSpec(itemForm.getItemSpec());
                entity.setItemIntroduce(itemForm.getItemIntroduce());

                entity.setItemCategoryEntity(catogoryEntity);
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

            //新修改
            if (itemForm.isInschool()) {
                int count = itemForm.getItemCount();
                for (int i = 0; i < count; i++) {
                    Item item = entity.clone();
                    item.setItemCount(1);
                    item.setInSchool(true);
                    entities.add(item);
                }
            } else {
                entities.add(entity);}
        });
        return entities;
    }

//    //将itemform转化为item对象
//    public List<Temp> toTemp(ItemForm itemForm){
//        List<Temp> temps = new ArrayList<>();
//
//        Temp temp = new Temp();
//
//        Item entity = Item.fromItemEnity(itemDao.findById(itemForm.getItemCode()));
//        if (entity != null) {
//            //如果数据库中本来就有的值，则只修改新的数量到item和新的价格
//            if (itemForm.getItemPrice() != 0) {
//                entity.setItemPrice(itemForm.getItemPrice());
//            }
//            entity.setNewItemCount(itemForm.getItemCount());
//        }else {
//            //如果不存在，则设置输入信息信息
//            entity = new Item();
//            entity.setInSchool(false);
//            logger.info("itemform name:" + itemForm.getItemName());
//            entity.setItemName(itemForm.getItemName());
//            ItemCompanyEntity companyEntity = new ItemCompanyEntity();
//            entity.setItemCode(itemForm.getItemCode());
//            entity.setPreItemCode(itemForm.getItemCode());
//            entity.setItemCount(itemForm.getItemCount());
//
//            entity.setItemSpec(itemForm.getItemSpec());
//            entity.setItemIntroduce(itemForm.getItemIntroduce());
//            companyEntity.setCompanyId(itemForm.getItemCompanyID());
//            entity.setItemCategoryId(itemForm.getItemCategoryID());
//            entity.setItemCompanyEntity(companyEntity);
//            entity.setItemPrice(itemForm.getItemPrice());
//            //默认值
//            entity.setItemBorrowTimelimit(0);
//            entity.setItemState("正常");
//            if (itemForm.isInschool()) {
//                entity.setItemExamine("手动");
//            } else entity.setItemExamine("自动");
//            entity.setItemRemind(0);
//        }
//      return entity;
//    }

    private static class Temp {
        private ItemInStorageEntity inStorageEntity;
        private ItemEntity itemEntity;
        private Item item;
    }

    private static class Item extends ItemEntity implements Cloneable {
        private boolean isInSchool;
        private String preItemCode;

        @Override
        public Item clone() {
            try {
                return (Item) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }

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
