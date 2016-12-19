package com.repository.service;

import com.repository.common.ApplyContants;
import com.repository.dao.DictionaryDao;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;
import com.repository.dao.ItemDao;
import com.repository.entity.DictionaryEntity;
import com.repository.entity.ItemApplicationEntity;
import com.repository.entity.ItemApplicationOperationEntity;
import com.repository.entity.ItemEntity;
import com.repository.util.Util;
import com.repository.web.apply.add.ApplyForm;
import com.repository.web.apply.add.ApplyItemForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/12/1.
 */
@Component
public class ApplyFormService {

    public static final String NO_STATES = "审核失败";
    public static final String ING_STATES = "正在审核";
    public static final String DEFAULT_STATES = ApplyContants.APPLY_NEED_EXAMINE;
    public static final String SUCCESS_STATES = ApplyContants.APPLY_NONEED_EXAMINE;


    @Autowired
    private ItemDao itemDao;
    @Autowired
    private DictionaryDao dictionaryDao;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private LogSerivce logSerivce;
    @Autowired
    private OutStorageService outStorageService;
    @Autowired
    private ItemApplicationOperationDao applicationOperationDao;

    @Transactional
    public boolean save(Principal principal, ApplyForm applyForm) {
        if (!check(applyForm)) {
            return false;
        }

        Session session = sessionFactory.getCurrentSession();
            //保存申请操作表
            ItemApplicationOperationEntity operationEntities = toApplyOpreation(applyForm);
            DictionaryEntity applicationIdEntity = dictionaryDao.findById("application_ID");
            operationEntities.setApplicationId((String.valueOf(Util.handleCode(applicationIdEntity))));
            applicationIdEntity.setIndex(applicationIdEntity.getIndex() + 1);

            session.save(operationEntities);
            session.update(applicationIdEntity);
            //保存申请表
            List<ItemApplicationEntity> items = toApplyItem(applyForm, operationEntities.getApplicationId());
            items.forEach(entity -> {
                session.save(entity);
                //log
                logSerivce.saveApply(principal.getName(), entity);
            });
            if (operationEntities.getStates().equals(DEFAULT_STATES)) {
                // 通知审核，减少item的数目
                outStorageService.saveNeedStorage(principal, operationEntities, items);
            } else if (operationEntities.getStates().equals(SUCCESS_STATES)) {
                outStorageService.saveAutoStorage(principal, operationEntities, items);
            }
            //日志记录
            logSerivce.saveApplyOpreation(principal.getName(), operationEntities);
        return true;
    }

    /**
     * 校对 数量是否正确
     */
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

    /**
     * 根据传入的参数，来判断审核单是否需要审核
     *
     * @param applyForm
     * @return true=need or false=no-need
     */
    public boolean needExamine(ApplyForm applyForm) {
        for (ApplyItemForm item : applyForm.getItems()) {
            if (itemDao.findById(item.getItemCode()).getItemExamine().trim().equals("手动")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据传入的参数，来判断审核单是否需要审核
     *
     * @param entities
     * @return need or no-need
     */
    public boolean needExamine(List<ItemApplicationEntity> entities) {
        for (ItemApplicationEntity item : entities) {
            if (itemDao.findById(item.getItemCode()).getItemExamine().trim().equals("手动")) {
                return true;
            }
        }
        return false;
    }


    @Autowired
    private ItemApplicationDao applicationDao;

    /**
     * 申请单的审核
     *
     * @param apply_id  申请单id
     * @param pass      true 通过审核，false，不通过审核
     * @param principal
     * @throws Exception 说明审核失败，出现了异常
     */
    @Transactional
    public void examine(Principal principal, String apply_id, boolean pass) throws Exception {
        if (pass) {
            outStorageService.outStorage(principal, apply_id);
        } else {
            ItemApplicationOperationEntity opera = applicationOperationDao.findById(apply_id);
            opera.setStates(ApplyContants.APPLY_FAIL_EXAMINE);
            opera.setStatesTime(new Date(System.currentTimeMillis()));
            opera.setExamineId(principal.getName());
            sessionFactory.getCurrentSession().update(opera);
            List<ItemApplicationEntity> applicationEntities = applicationDao.findByApplyId(apply_id);
            applicationEntities.forEach(e -> {
                ItemEntity item = itemDao.findById(e.getItemCode());
                item.setItemCount(item.getItemCount() + e.getCounts());
                sessionFactory.getCurrentSession().update(item);
            });
        }
    }
}
