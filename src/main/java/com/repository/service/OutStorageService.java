package com.repository.service;

import com.repository.dao.*;
import com.repository.entity.*;
import com.repository.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/12/1.
 */
@Component
@Repository
@Transactional
public class OutStorageService {

    @Autowired
    DictionaryDao dictionaryDao;

    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemInOperationDao operationDao;
    @Autowired
    ItemInStorageDao storageDao;
    @Autowired
    LogSerivce logSerivce;
    @Autowired
    CompanyDao companyDao;
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ItemApplicationDao applicationDao;

    @Autowired
    ItemApplicationOperationDao applicationOperationDao;
    @Autowired
    ItemInStorageDao inStorageDao;

    @Transactional
    public void outStorage(Principal principal, String apply_id) {
        ItemApplicationOperationEntity operationEntity = applicationOperationDao.findById(apply_id);
        List<ItemApplicationEntity> items = applicationDao.findByApplyId(apply_id);
        saveStorage(principal, operationEntity, items);
    }

    /**
     * 出库一个出库单
     */
    @Transactional
    public void saveStorage(Principal principal, ItemApplicationOperationEntity operationEntities, List<ItemApplicationEntity> items) {
        ItemOutOperationEntity outOperationEntity = toOutOprea(operationEntities);
        Session session = sessionFactory.getCurrentSession();
        //保存出库操作表
        DictionaryEntity applicationIdEntity = dictionaryDao.findById("out_ID");
        outOperationEntity.setOutId(String.valueOf(Util.handleCode(applicationIdEntity)));
        applicationIdEntity.setIndex(applicationIdEntity.getIndex() + 1);
        session.update(applicationIdEntity);
        session.save(outOperationEntity);

        logSerivce.saveOutOpera(principal.getName(), outOperationEntity);

        //保存出库表
        List<ItemOutStorageEntity> outStorageEntities = toOutStorage(items, outOperationEntity.getOutId());
        outStorageEntities.forEach(out -> {
            session.save(out);
//            Util.changecount()
            ItemEntity itemEntity = itemDao.findById(out.getItemCode());
            itemEntity.setItemCount(itemEntity.getItemCount() - out.getCounts());
            //修改批次相关信息
            changeCount(out.getItemCode(), out.getCounts());
            session.update(itemEntity);

            logSerivce.saveOutStorage(principal.getName(), out);
        });

        System.out.println("执行了日志和消息");

        messageService.send(new MessageService.SendBuilder(principal.getName()).title("出库完成").receId(principal.getName()).content("请到仓库1，取出物品")
                .type("出库").send());
        //修改item表的数量
    }

    @Autowired
    MessageService messageService;

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    private List<ItemInStorageEntity> getList(String itemCode) {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("select * from item_in_storage where allow_count>0 and item_code=" + itemCode + " order by item_indate").addEntity(ItemInStorageEntity.class).list();
    }

    @Transactional
    public void changeCount(String itemCode, int counts) {
        Session session = sessionFactory.getCurrentSession();
        List<ItemInStorageEntity> list = Util.changecount(getList(itemCode), counts);
        list.forEach(e -> {
            session.update(e);
        });
    }

    public ItemOutOperationEntity toOutOprea(ItemApplicationOperationEntity operationEntities) {
        ItemOutOperationEntity outStorageEntity = new ItemOutOperationEntity();
        outStorageEntity.setUsersId(operationEntities.getUsersId());
        outStorageEntity.setApplyId(operationEntities.getApplicationId());
        outStorageEntity.setOperationId("10000");
        outStorageEntity.setOutAddress("测试");
        outStorageEntity.setOutStates("正在完成");
        outStorageEntity.setOutTime(new Date(System.currentTimeMillis()));
        outStorageEntity.setOutId(dictionaryDao.getOutStorageId());
        return outStorageEntity;
    }

    public List<ItemOutStorageEntity> toOutStorage(List<ItemApplicationEntity> itemApplicationEntityList, String outId) {
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
