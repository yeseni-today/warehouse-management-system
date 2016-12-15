package com.repository.service;

import com.repository.dao.MessageDao;
import com.repository.entity.MessageEntity;
import com.repository.model.ItemIndate;
import com.repository.web.message.MessageForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/30.
 */
@Component
@Repository
@Transactional
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    SessionFactory sessionFactory;

    public static final String DEFAULT_MESSAGE_TYPE = "系统消息";
    public static final String STATES_MESSAGE_TYPE = "系统消息";

    public boolean send(MessageForm messageForm) {
        try {
            messageDao.save(toMessage(messageForm));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean send(MessageEntity messageForm) {
        try {
            messageDao.save(messageForm);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public MessageEntity findById(String id) {
        return messageDao.findById(id);
    }

    @Transactional
    public List<MessageEntity> findWranMessage() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select *  from vcyk_itemindate";
        List<ItemIndate> indates = session.createSQLQuery(sql).addEntity(ItemIndate.class).list();
        List<MessageEntity> result = new ArrayList<>();
        indates.forEach(indate -> {
            MessageEntity msg = new MessageEntity();
            msg.setMessageState(MessageEntity.State.UNREAD);
            msg.setMessageTitle("提醒");
            //提醒内容
            msg.setMessageContent(indate.toString());
            msg.setMessageType("提醒");
            msg.setMessageDate(new Timestamp(System.currentTimeMillis()));
            result.add(msg);
        });
        return result;
    }


    public void read(String msgId) {
        MessageEntity msg = messageDao.findById(msgId);
        msg.setMessageState(MessageEntity.State.READ);
        messageDao.update(msg);
    }

    public void delete(String msgId) {
        MessageEntity msg = messageDao.findById(msgId);
        msg.setMessageState(MessageEntity.State.DELETE);
        messageDao.update(msg);
    }

    public List<MessageEntity> findMessage(String receiveId) {
        return messageDao.query(new String[]{"messageReceiveId"}, new String[]{receiveId}, false);
    }


    public void delete(MessageForm messageForm) {
        messageDao.delete(toMessage(messageForm));
    }


    private MessageEntity toMessage(MessageForm messageForm) {
        MessageEntity umessage = new MessageEntity();
        umessage.setMessageContent(messageForm.getMessageContent());
        umessage.setMessageDate(new Timestamp(System.currentTimeMillis()));
        umessage.setMessageReceiveId(messageForm.getReceive_ID());
        umessage.setMessageSendId(messageForm.getSend_ID());
        umessage.setMessageType(DEFAULT_MESSAGE_TYPE);
        umessage.setMessageTitle(messageForm.getMessageTitle());
        umessage.setMessageState(MessageEntity.State.UNREAD);
        return umessage;
    }

    public static class SendBuilder {

        private MessageEntity.State state = MessageEntity.State.UNREAD;
        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        private String content = "默认消息内容";
        private String type = "默认消息类型";
        private String receId = "0000";
        private String title = "默认消息标题";
        private String sendId;

        public SendBuilder(String sendId) {
            this.sendId = sendId;
        }

        public SendBuilder content(String content) {
            this.content = content;
            return this;
        }

        public SendBuilder type(String type) {
            this.type = type;
            return this;
        }

        public SendBuilder receId(String receId) {
            this.receId = receId;
            return this;
        }

        public SendBuilder title(String title) {
            this.title = title;
            return this;
        }


        public MessageEntity send() {
            MessageEntity message = new MessageEntity();
            message.setMessageDate(timestamp);
            message.setMessageState(state);
            message.setMessageTitle(title);
            message.setMessageType(type);
            message.setMessageContent(content);
            message.setMessageReceiveId(receId);
            message.setMessageSendId(sendId);
          return message;
        }
    }

}