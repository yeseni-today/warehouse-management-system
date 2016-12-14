package com.repository.service;

import com.repository.dao.LogDao;
import com.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/27.
 */
@Component
@Transactional
public class LogSerivce {

    @Autowired
    LogDao logDao;
    //log id
    //log 类型
    //操作人id
    //logdate
    //log level
    //log table
    //loginfo
    //log annotation

    public static final String QUERY_TYPE = "query";
    public static final String QUERY_ITEM_INFO = "物品查询：";
    public static final String QUERY_ITEM_LEVEL = "info";

    public static final String SAVE_TYPE = "save";
    public static final String SAVE_ITEM_LEVEL = "important";

    public static final String TABLE_ITEM = "items";
    public static final String TABLE_INOPERATION = "item_in_opreation";
    public static final String TABLE_INSTORAGE = "item_in_storage";
    public static final String TABLE_OUTSTORAGE = "item_out_storage";
    public static final String TABLE_OUTSTORAGE_OPREATION = "item_out_opreation";
    public static final String TABLE_APPLY = "item_application";
    public static final String TABLE_APPLY_OPEARTION = "item_application_opreation";
    public static final String TABLE_MESSAGE = "item_umessage";

    public List<LogEntity> findAll(){
        return logDao.findAll();
    }

    public LogEntity findById(String id){
        return logDao.findById(id);
    }

    public  List<LogEntity> findByType(String type){
        return logDao.query(new String[]{"logType"},new String[]{type},false);
    }

    public void saveOutOpera(String opreation_id, ItemOutOperationEntity entity) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_OUTSTORAGE_OPREATION)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(entity.getApplyId())
                        .build()
        );
    }

    public void saveOutStorage(String opreation_id, ItemOutStorageEntity out) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_OUTSTORAGE)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(out.toString())
                        .build()
        );
    }


    @Transactional
    public void saveMessage(String opreation_id, MessageEntity operationEntities) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_MESSAGE)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(operationEntities.toString())
                        .build()
        );
    }

    @Transactional
    public void saveApplyOpreation(String opreation_id, ItemApplicationOperationEntity operationEntities) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_APPLY_OPEARTION)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(operationEntities.toString())
                        .build()
        );
    }

    @Transactional
    public void saveApply(String opreation_id, ItemApplicationEntity inStorageEntity) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_APPLY)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(inStorageEntity.toString())
                        .build()
        );
    }

    @Transactional
    public void saveInStorage(String opreation_id, ItemInStorageEntity inStorageEntity) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_INSTORAGE)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(inStorageEntity.toString())
                        .build()
        );
    }

    @Transactional
    public void saveInOpreation(String opreation_id, ItemInOperationEntity inOperationEntity) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_INOPERATION)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(inOperationEntity.toString())
                        .build()
        );
    }

    @Transactional
    public void saveItem(String opreation_id, ItemEntity itemEntity) {
        logDao.save(
                new LogBuilder()
                        .table(TABLE_ITEM)
                        .level(SAVE_ITEM_LEVEL)
                        .type(SAVE_TYPE)
                        .opreationID(opreation_id)
                        .info(itemEntity.toString())
                        .build()
        );
    }

    public void queryItem(String opreation_id, String parm) {
        queryItem(opreation_id, parm, null);
    }

    public void queryItem(String opreation_id, String parm, String annotation) {
        LogEntity log = new LogEntity();
        log.setLogTable(TABLE_ITEM);
        log.setLogType(QUERY_TYPE);
        log.setLogDate(new Timestamp(System.currentTimeMillis()));
        log.setLogInfo(QUERY_ITEM_INFO);
        log.setLogLevel(QUERY_ITEM_LEVEL);
        log.setOperationId(opreation_id);
        log.setLogInfo(QUERY_ITEM_INFO + parm);
        log.setLogAnnonation(annotation);
        logDao.save(log);
    }

    /**
     * todo 维护日志
     * */
    public List<LogEntity> findMaintain() {
       return new ArrayList<>();
    }
    /**
     * todo 借用日志
     * */
    public List<LogEntity> findBorrow() {
        return new ArrayList<>();
    }

    public List<LogEntity> findApply() {
        return logDao.query("logTable", TABLE_APPLY);
    }

    public List<LogEntity> findOutstorage() {
        return logDao.query("logTable",TABLE_OUTSTORAGE);
    }

    public List<LogEntity> findInstorage() {
        return logDao.query("logTable",TABLE_INSTORAGE);
    }

    /**
     * todo 系统日志
     * */
    public List<LogEntity> findSystem() {
        return new ArrayList<>();
    }



    public static class LogBuilder {
        String table;
        String type;
        Timestamp date;
        String info;
        String level;
        String opreationID;
        String annotation;

        public LogBuilder() {
        }

        public LogBuilder table(String table) {
            this.table = table;
            return this;
        }

        public LogBuilder type(String type) {
            this.type = type;
            return this;
        }

        public LogBuilder table(Timestamp date) {
            this.date = date;
            return this;
        }

        public LogBuilder info(String info) {
            this.info = info;
            return this;
        }

        public LogBuilder level(String level) {
            this.level = level;
            return this;
        }

        public LogBuilder opreationID(String opreationID) {
            this.opreationID = opreationID;
            return this;
        }

        public LogBuilder annotation(String annotation) {
            this.annotation = annotation;
            return this;
        }

        public LogEntity build() {
            if (date == null) {
                date = new Timestamp(System.currentTimeMillis());
            }
            LogEntity log = new LogEntity();
            log.setLogTable(table);
            log.setLogType(type);
            log.setLogDate(date);
            log.setLogInfo(info);
            log.setLogLevel(level);
            log.setOperationId(opreationID);
            log.setLogAnnonation(annotation);
            return log;
        }

    }
}
