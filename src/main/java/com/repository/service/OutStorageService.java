package com.repository.service;

import com.repository.common.ApplyContants;
import com.repository.common.Constants;
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
    ItemOutOperationDao _outOperationDao;
    @Autowired
    ItemInStorageDao inStorageDao;

    public boolean outState(String out_id, boolean isSuccess) {
        ItemOutOperationEntity outOperationEntity = _outOperationDao.findById(out_id);

        if (outOperationEntity == null){
            return false;
        }

        if (isSuccess){
            outOperationEntity.setOutStates(Constants.OUTSTOAGR_SUCCESS_STATUS);
        }else {
            outOperationEntity.setOutStates(Constants.OUTSTOAGR_FAIL_STATUS);
        }
        _outOperationDao.update(outOperationEntity);
        return true;
    }

    /**
     * 出库一个申请单
     * */
    @Transactional
    public void outStorage(Principal principal, String apply_id) {
        ItemApplicationOperationEntity operationEntity = applicationOperationDao.findById(apply_id);
        List<ItemApplicationEntity> items = applicationDao.findByApplyId(apply_id);
        operationEntity.setExamineId(principal.getName());
        operationEntity.setStates(ApplyContants.APPLY_NONEED_EXAMINE);
        operationEntity.setStatesTime(new Date(System.currentTimeMillis()));
        applicationOperationDao.update(operationEntity);
        saveStorage(principal, operationEntity, items);
    }

    /**
    * 保持一个需要审核的申请单，需要先修改物品信息
    * */
    @Transactional
    public void saveNeedStorage(Principal principal, ItemApplicationOperationEntity operationEntities, List<ItemApplicationEntity> items) {
        // 发送消息给管理员
        messageService.send(
                new MessageService.SendBuilder(principal.getName())
                        .content("审核")
                        .type("审核")
                        .receId("admin")
                        .title("需要审核")
                        .build()
        );
        //修改物品信息
        items.forEach(e -> changeItemCount(e.getItemCode(),e.getCounts()));
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
            //先保存库出单
            session.save(out);
//            changeItemCount(out.getItemCode(), out.getCounts());
            changeBatchCount(out.getItemCode(),out.getCounts());
            logSerivce.saveOutStorage(principal.getName(), out);
        });
        messageService.send(
                new MessageService.SendBuilder(principal.getName())
                        .title("出库信息")
                        .receId(outOperationEntity.getUsersId())
                        .content("请到仓库1，取出物品")
                        .type("出库").build());
        System.out.println(outOperationEntity.getUsersId());
    }

    @Autowired
    MessageService messageService;

    @Autowired
    SessionFactory sessionFactory;


    @Transactional
    public void changeItemCount(String itemCode, int count) {
        //修改物品表中对应的物品数量
        ItemEntity itemEntity = itemDao.findById(itemCode);
        itemEntity.setItemCount(itemEntity.getItemCount() - count);
        sessionFactory.getCurrentSession().update(itemEntity);
    }

    @Transactional
    public void changeBatchCount(String itemCode, int counts) {
        Session session = sessionFactory.getCurrentSession();
        List<ItemInStorageEntity> list = Util.changecount(getList(itemCode), counts);
        list.forEach(session::update);
    }

    /**
     * 通过id来物品的入库单，用于获取对应的批次信息
     *
     * @param itemCode 物品id
     * @return list ItemInStoragEntity
     */
    @Transactional
    private List<ItemInStorageEntity> getList(String itemCode) {
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery("select * from item_in_storage where allow_count>0 and item_code='" + itemCode + "'" + " order by item_indate").addEntity(ItemInStorageEntity.class).list();
    }


    public ItemOutOperationEntity toOutOprea(ItemApplicationOperationEntity operationEntities) {
        ItemOutOperationEntity outStorageEntity = new ItemOutOperationEntity();
        outStorageEntity.setUsersId(operationEntities.getUsersId());
        outStorageEntity.setApplyId(operationEntities.getApplicationId());
        outStorageEntity.setOperationId("10000");
        outStorageEntity.setOutAddress(Constants.OUTSTOAGR_DEFAULT_ADDRESS);
        outStorageEntity.setOutStates(Constants.OUTSTOAGR_DEFAULT_STATUS);
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

    public void saveAutoStorage(Principal principal, ItemApplicationOperationEntity operationEntities, List<ItemApplicationEntity> items) {
        items.forEach(e -> changeItemCount(e.getItemCode(),e.getCounts()));
        saveStorage(principal, operationEntities, items);
    }


    //出库操作表（出库单编号，对应申请(借取)单编号，出库日期，领取人ID，领取地点，出库状态，操作人员ID）
}
