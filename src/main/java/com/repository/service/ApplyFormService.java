package com.repository.service;

import com.repository.dao.CategoryDao;
import com.repository.dao.CompanyDao;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.dao.DictionaryDao;
import com.repository.entity.ItemApplicationEntity;
import com.repository.entity.ItemApplicationOperationEntity;
import com.repository.entity.DictionaryEntity;
import com.repository.util.Util;
import com.repository.web.apply.add.ApplyForm;
import com.repository.web.apply.add.ApplyItemForm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/12/1.
 */
@Component
public class ApplyFormService {

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
    @Autowired
    OutStorageService outStorageService;
    @Autowired
    MessageService messageService;

    @Transactional
    public boolean save(Principal principal, ApplyForm applyForm) {
        if (!check(applyForm)) {
            return false;
        }

        Session session = sessionFactory.getCurrentSession();
        try {
            //保存入库操作表
            ItemApplicationOperationEntity operationEntities = toApplyOpreation(applyForm);
            DictionaryEntity applicationIdEntity = dictionaryDao.findById("application_ID");
            operationEntities.setApplicationId((String.valueOf(Util.handleCode(applicationIdEntity))));
            applicationIdEntity.setIndex(applicationIdEntity.getIndex() + 1);

            session.save(operationEntities);
            session.update(applicationIdEntity);
            //保存入库表
            List<ItemApplicationEntity> items = toApplyItem(applyForm, operationEntities.getApplicationId());
            items.forEach(entity -> {
                session.save(entity);
                //log
                logSerivce.saveApply(principal.getName(),entity);
            });
            if (operationEntities.getStates().equals(DEFAULT_STATES)) {
                //todo 发送消息给管理员
                //messageService.send();
                new MessageService.SendBuilder(principal.getName())
                        .content("审核")
                        .type("审核")
                        .receId("admin")
                        .title("需要审核")
                        .send();
            } else if (operationEntities.getStates().equals(SUCCESS_STATES)) {
                outStorageService.saveAutoStoage(principal,operationEntities, items);
            }

            //日志记录
            logSerivce.saveApplyOpreation(principal.getName(),operationEntities);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public boolean check(ApplyForm applyForm) {
        for (ApplyItemForm item : applyForm.getItems()) {
            if (itemDao.findById(item.getItemCode()).getItemCount() < item.getItemCount()) {
                return false;
            }
        }
        return true;
    }

    public List<ItemApplicationEntity> toApplyItem(ApplyForm applyForm, String applyId) {
        List<ItemApplicationEntity> result = new ArrayList<>();
        applyForm.getItems().forEach(item -> {
            ItemApplicationEntity application = new ItemApplicationEntity();
            application.setApplicationId(applyId);
            application.setApplicationText(item.getOthers());
            application.setApplicationType(item.getApplyType());
            application.setCounts(item.getItemCount());
            application.setItemCode(item.getItemCode());
            result.add(application);
        });
        return result;
    }

    public static final String NO_STATES = "审核失败";
    public static final String ING_STATES = "正在审核";
    public static final String DEFAULT_STATES = "未审核";
    public static final String SUCCESS_STATES = "审核通过";

    public ItemApplicationOperationEntity toApplyOpreation(ApplyForm applyForm) {
        ItemApplicationOperationEntity operationEntity = new ItemApplicationOperationEntity();
        operationEntity.setApplicationId(applyForm.getApplicationId());
        operationEntity.setApplicationTime(new Date(System.currentTimeMillis()));

        if (needExamine(applyForm)) {
            operationEntity.setStates(DEFAULT_STATES);
            operationEntity.setStatesTime(new Date(0));
            operationEntity.setExamineId("00000");
        } else {
            operationEntity.setStates(SUCCESS_STATES);
            operationEntity.setStatesTime(new Date(System.currentTimeMillis()));
            operationEntity.setExamineId("10000");
        }

        operationEntity.setUsersId(applyForm.getUsersId());

        return operationEntity;
    }

    public boolean needExamine(ApplyForm applyForm) {
        for (ApplyItemForm item : applyForm.getItems()) {
            if (itemDao.findById(item.getItemCode()).getItemExamine().trim().equals("手动")) {
                return true;
            }
        }
        return false;
    }

    public boolean needExamine(List<ItemApplicationEntity> entities) {
        for (ItemApplicationEntity item : entities) {
            if (itemDao.findById(item.getItemCode()).getItemExamine().trim().equals("手动")) {
                return true;
            }
        }
        return false;
    }


}