package com.repository.service;

import com.repository.dao.UmessageDao;
import com.repository.entity.UmessageEntity;
import com.repository.model.ItemIndate;
import com.repository.web.message.MessageForm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/11/30.
 */
@Component
public class MessageService {

    @Autowired
    UmessageDao umessageDao;

    @Autowired
    SessionFactory sessionFactory;

    public boolean send(MessageForm messageForm) {
        try {
            umessageDao.save(toMessage(messageForm));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public List<UmessageEntity> findWranMessage() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select *  from vcyk_itemindate";
        List<ItemIndate> indates = session.createSQLQuery(sql).addEntity(ItemIndate.class).list();
        List<UmessageEntity> result = new ArrayList<>();
        indates.forEach(indate -> {
            UmessageEntity msg = new UmessageEntity();
            msg.setMessageState(UmessageEntity.State.UNREAD);
            msg.setMessageTitle("提醒");
            msg.setMessageContent(indate.toString());
            msg.setMessageType("提醒");
            msg.setMessageDate(new Timestamp(System.currentTimeMillis()));
            result.add(msg);
        });
        return result;
    }

    public void read(String msgId) {
        UmessageEntity msg = umessageDao.findById(msgId);
        msg.setMessageState(UmessageEntity.State.READ);
        umessageDao.update(msg);
    }

    public void delete(String msgId) {
        UmessageEntity msg = umessageDao.findById(msgId);
        msg.setMessageState(UmessageEntity.State.DELETE);
        umessageDao.update(msg);
    }

    public List<UmessageEntity> findMessage(String receiveId) {
        return umessageDao.query(new String[]{"messageReceiveId"}, new String[]{receiveId}, false);
    }


    public void delete(MessageForm messageForm) {
        umessageDao.delete(toMessage(messageForm));
    }


    private UmessageEntity toMessage(MessageForm messageForm) {
        UmessageEntity umessage = new UmessageEntity();
        umessage.setMessageContent(messageForm.getMessageContent());
        umessage.setMessageDate(new Timestamp(System.currentTimeMillis()));
        umessage.setMessageReceiveId(messageForm.getReceive_ID());
        umessage.setMessageSendId(messageForm.getSend_ID());
        umessage.setMessageType(DEFAULT_MESSAGE_TYPE);
        umessage.setMessageTitle(messageForm.getMessageTitle());
        umessage.setMessageState(UmessageEntity.State.UNREAD);
        return umessage;
    }

    public static final String DEFAULT_MESSAGE_TYPE = "系统消息";
}
