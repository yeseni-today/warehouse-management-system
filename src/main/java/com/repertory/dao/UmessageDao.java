package com.repertory.dao;

import com.repertory.bean.UmessageEntity;

import java.sql.Timestamp;

/**
 * Created by Finderlo on 2016/11/4.
 */
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
