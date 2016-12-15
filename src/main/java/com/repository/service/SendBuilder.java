//package com.repository.service;
//
//import com.repository.dao.MessageDao;
//import com.repository.entity.MessageEntity;
//import groovy.util.logging.Commons;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//
///**
// * Created by finderlo on 16-12-14.
// */
//
//public  class SendBuilder {
//
//    MessageDao messageDao = new MessageDao();
//
//    private MessageEntity.State state = MessageEntity.State.UNREAD;
//    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//    private String content = "默认消息内容";
//    private String type = "默认消息类型";
//    private String receId = "0000";
//    private String title = "默认消息标题";
//    private String sendId;
//
//    public SendBuilder(String sendId) {
//        this.sendId = sendId;
//    }
//
//    public SendBuilder content(String content) {
//        this.content = content;
//        return this;
//    }
//
//    public SendBuilder type(String type) {
//        this.type = type;
//        return this;
//    }
//
//    public SendBuilder receId(String receId) {
//        this.receId = receId;
//        return this;
//    }
//
//    public SendBuilder title(String title) {
//        this.title = title;
//        return this;
//    }
//
//
//    public void send() {
//        MessageEntity message = new MessageEntity();
//        message.setMessageDate(timestamp);
//        message.setMessageState(state);
//        message.setMessageTitle(title);
//        message.setMessageType(type);
//        message.setMessageContent(content);
//        message.setMessageReceiveId(receId);
//        message.setMessageSendId(sendId);
//        messageDao.save(message);
//    }
//}