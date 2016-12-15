package com.repository.service;

import com.repository.base.BaseObject;
import com.repository.dao.*;
import com.repository.entity.*;
import com.repository.util.Util;
import com.repository.web.storage.add.StorageForm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    DictionaryDao dictionaryDao;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    LogSerivce logSerivce;
    @Autowired
    CompanyDao companyDao;
    @Autowired
    CategoryDao categoryDao;


    @Transactional
    public void save(Principal principal, StorageForm storageForm) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            //转化为compounds，进行转化
            List<ItemCompound> compounds = formToComound(storageForm);

            for (ItemCompound compound : compounds) {
                if (compound.isInSchool) {
                    //如果是校内编码
                    for (ItemCompound itemCompound : compound.itemCompoundList) {
                        //获取字典用于编号
                        DictionaryEntity dictionaryEntity = dictionaryDao.findById(itemCompound.itemEntity.getCategoryEntity().getCategoryId());
                        //通过字典设置编号
                        itemCompound.itemEntity.setItemCode(Util.handleCode(dictionaryEntity));
                        itemCompound.itemInStorageEntity.setItemCode(itemCompound.itemEntity.getItemCode());
                        //字典index+1
                        dictionaryEntity.setIndex(dictionaryEntity.getIndex() + 1);
                        //持久化
                        session.save(itemCompound.itemEntity);
                        session.saveOrUpdate(dictionaryEntity);
                        //log
                        logSerivce.saveItem(principal.getName(), itemCompound.itemEntity);
                    }
                } else {

                    ItemEntity itemEntity = itemDao.findById(compound.itemEntity.getItemCode());
                    if (itemEntity != null) {
                        //校内已经存在，则设置新的价格和新的数量
                        itemEntity.setItemCount(itemEntity.getItemCount() + compound.itemEntity.getItemCount());
                        session.save(itemEntity);
                        //log
                        logSerivce.saveItem(principal.getName(), itemEntity);
                    } else {
                        //校内不存在，直接保存
                        session.save(compound.itemEntity);
                        //log
                        logSerivce.saveItem(principal.getName(), compound.itemEntity);
                    }
                }
            }
            //保存操作表
            ItemInOperationEntity operationEntity = formToInOpreation(storageForm);
            //通过字典设置编号
            DictionaryEntity dictionaryEntity = dictionaryDao.findById("storage_ID");
            operationEntity.setStorageId(Util.handleCode(dictionaryEntity));
            //字典index+1
            dictionaryEntity.setIndex(dictionaryEntity.getIndex() + 1);
            //持久化
            session.save(operationEntity);
            session.saveOrUpdate(dictionaryEntity);
            //log
            logSerivce.saveInOpreation(principal.getName(), operationEntity);
            //遍历保存入库表
            for (ItemCompound e : compounds) {

                if (e.isInSchool) {
                    //如果是校内，由于校内的个数只有1，多次保存，编码也不同
                    e.itemCompoundList.forEach(compound -> {
                                session.save(compound.itemInStorageEntity);
                                logSerivce.saveInStorage(principal.getName(), compound.itemInStorageEntity);
                            });
                } else {
                    //不是校内，直接保存
                    session.save(e.itemInStorageEntity);
                    logSerivce.saveInStorage(principal.getName(), e.itemInStorageEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemInOperationEntity formToInOpreation(StorageForm storageForm) {
        ItemInOperationEntity entity = new ItemInOperationEntity();
        entity.setStorageTime(new java.sql.Date(System.currentTimeMillis()));
        entity.setOperationId(storageForm.getOpreationId());
        entity.setStorageId(storageForm.getInStorageId());
        return entity;
    }

    public List<ItemCompound> formToComound(StorageForm storageForm) {
        List<ItemCompound> compounds = new ArrayList<>();
        storageForm.getItemForms().forEach(itemForm -> {
            ItemCompound compound = new ItemCompound();

            if (itemForm.isInschool()) {
                //如果是校内编码，生成多个对象保存到compound中，实行一物一码
                compound.isInSchool = true;
                ItemCompound compoundinschool = new ItemCompound();

                ItemInStorageEntity storageEntity = new ItemInStorageEntity();
                storageEntity.setStorageId(storageForm.getInStorageId());
                storageEntity.setPrice(itemForm.getItemPrice());
                storageEntity.setItemIndate(new java.sql.Date(System.currentTimeMillis()));
                storageEntity.setItemCode(itemForm.getItemCode());
                storageEntity.setBillCode(itemForm.getBillCode());
                storageEntity.setCounts(itemForm.getItemCount());
                storageEntity.setItemBatch(itemForm.getItemBatch());
                storageEntity.setItemSlot(itemForm.getItemSlot());
                storageEntity.setAllowCount(1);
                storageEntity.setCounts(1);

                ItemEntity itemEntity = new ItemEntity();
                //如果不存在，则设置输入信息信息
                logger.info("itemform name:" + itemForm.getItemName());
                itemEntity.setItemName(itemForm.getItemName());
                CompanyEntity companyEntity = companyDao.findById(itemForm.getItemCompanyID());
                CategoryEntity catogoryEntity = categoryDao.findById(itemForm.getItemCategoryID());
                itemEntity.setItemCode(itemForm.getItemCode());
                itemEntity.setItemCount(1);  //一物一码count=1

                itemEntity.setItemSpec(itemForm.getItemSpec());
                itemEntity.setItemIntroduce(itemForm.getItemIntroduce());

                itemEntity.setCategoryEntity(catogoryEntity);
                itemEntity.setCompanyEntity(companyEntity);
                itemEntity.setItemPrice(itemForm.getItemPrice());
                //默认值
                itemEntity.setItemBorrowTimelimit(0);
                itemEntity.setItemState("正常");
                if (itemForm.isInschool()) {
                    itemEntity.setItemExamine("手动");
                } else itemEntity.setItemExamine("自动");
                itemEntity.setItemRemind(0);

                compoundinschool.itemEntity = itemEntity;
                compoundinschool.itemInStorageEntity = storageEntity;

                for (int i = 0; i < itemForm.getItemCount(); i++) {
                    ItemCompound temp = new ItemCompound();
                    temp.itemEntity = itemEntity.clone();
                    temp.itemInStorageEntity = storageEntity.clone();
                    compound.itemCompoundList.add(temp);
                }


            } else {
                //非校内编码
                ItemInStorageEntity inStorageEntity = new ItemInStorageEntity();
                inStorageEntity.setStorageId(storageForm.getInStorageId());
                inStorageEntity.setPrice(itemForm.getItemPrice());
                inStorageEntity.setItemIndate(new java.sql.Date(System.currentTimeMillis()));
                inStorageEntity.setItemCode(itemForm.getItemCode());
                inStorageEntity.setAllowCount(itemForm.getItemCount());
                inStorageEntity.setBillCode(itemForm.getBillCode());
                inStorageEntity.setCounts(itemForm.getItemCount());
                inStorageEntity.setItemBatch(itemForm.getItemBatch());
                inStorageEntity.setItemSlot(itemForm.getItemSlot());


                ItemEntity itemEntity = new ItemEntity();

                logger.info("itemform name:" + itemForm.getItemName());
                itemEntity.setItemName(itemForm.getItemName());
                CompanyEntity companyitemEntity = companyDao.findById(itemForm.getItemCompanyID());
                CategoryEntity catogoryitemEntity = categoryDao.findById(itemForm.getItemCategoryID());
                itemEntity.setItemCode(itemForm.getItemCode());
                itemEntity.setItemCount(itemForm.getItemCount());

                itemEntity.setItemSpec(itemForm.getItemSpec());
                itemEntity.setItemIntroduce(itemForm.getItemIntroduce());

                itemEntity.setCategoryEntity(catogoryitemEntity);
                itemEntity.setCompanyEntity(companyitemEntity);
                itemEntity.setItemPrice(itemForm.getItemPrice());
                //默认值
                itemEntity.setItemBorrowTimelimit(0);
                itemEntity.setItemState("正常");
                if (itemForm.isInschool()) {
                    itemEntity.setItemExamine("手动");
                } else itemEntity.setItemExamine("自动");
                itemEntity.setItemRemind(0);

                compound.itemInStorageEntity = inStorageEntity;
                compound.itemEntity = itemEntity;
            }

            compounds.add(compound);
        });
        return compounds;
    }

    private static class ItemCompound implements Cloneable {
        ItemEntity itemEntity;
        ItemInStorageEntity itemInStorageEntity;

        boolean isInSchool;
        List<ItemCompound> itemCompoundList = new ArrayList<>();

    }

}
