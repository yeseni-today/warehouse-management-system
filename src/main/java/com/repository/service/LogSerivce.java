package com.repository.service;

import com.repository.dao.SLogDao;
import com.repository.entity.ItemEntity;
import com.repository.entity.ItemInOperationEntity;
import com.repository.entity.ItemInStorageEntity;
import com.repository.entity.SlogEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/11/27.
 */
@Component
@Transactional
public class LogSerivce {

    @Autowired
    SLogDao logDao;
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

    public static final String ITEM_TABLE = "items";
    public static final String INOPERATION_TABLE = "item_in_opreation";
    public static final String INSTORAGE_TABLE = "item_in_storage";

    @Transactional
    public void saveInStorage(String opreation_id, ItemInStorageEntity inStorageEntity) {
        logDao.save(
                new LogBuilder()
                        .table(INSTORAGE_TABLE)
                        .type(SAVE_TYPE)
                        .level(SAVE_ITEM_LEVEL)
                        .opreationID(opreation_id)
                        .info(inStorageEntity.toString())
                        .build()
        );
    }

    @Transactional
    public void saveInOpreation(String opreation_id, ItemInOperationEntity inOperationEntity) {
//        logDao.save(
//                new LogBuilder()
//                .table(INOPERATION_TABLE)
//                .type(SAVE_TYPE)
//                .level(SAVE_ITEM_LEVEL)
//                .opreationID(opreation_id)
//                .info(inOperationEntity.toString())
//                .build()
//        );
    }

    @Transactional
    public void saveItem(String opreation_id, ItemEntity itemEntity) {
        logDao.save(
                new LogBuilder()
                        .table(ITEM_TABLE)
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
        SlogEntity log = new SlogEntity();
        log.setLogTable(ITEM_TABLE);
        log.setLogType(QUERY_TYPE);
        log.setLogDate(new Timestamp(System.currentTimeMillis()));
        log.setLogInfo(QUERY_ITEM_INFO);
        log.setLogLevel(QUERY_ITEM_LEVEL);
        log.setOperationId(opreation_id);
        log.setLogInfo(QUERY_ITEM_INFO + parm);
        log.setLogAnnonation(annotation);
        logDao.save(log);
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

        public SlogEntity build() {
            if (date == null) {
                date = new Timestamp(System.currentTimeMillis());
            }
            SlogEntity log = new SlogEntity();
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
