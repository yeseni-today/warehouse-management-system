package com.repository.dao;

import com.repository.entity.UmessageEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Component
@Repository
public class UmessageDao extends AbstractDao<UmessageEntity> {


    public static void main(String[] args) {
        UmessageEntity umessageEntity = new UmessageEntity();
        umessageEntity.setMessageId("id66666");
        umessageEntity.setMessageType("type44");
        umessageEntity.setMessageContent("444444");
        umessageEntity.setMessageDate(new Timestamp(System.currentTimeMillis()));
        umessageEntity.setMessageReceiveId("reerer");
        umessageEntity.setMessageSendId("sendid");

        UmessageDao umessageDao = new UmessageDao();

        umessageDao.save(umessageEntity);
    }


}
