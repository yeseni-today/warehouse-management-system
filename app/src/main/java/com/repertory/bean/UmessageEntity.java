package com.repertory.bean;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "umessage", schema = "wms")
public class UmessageEntity {
    private String messageId;
    private String messageType;
    private String messageContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp messageDate;
    private String messageSendId;
    private String messageReceiveId;

    @Id
    @Column(name = "message_ID")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "message_type")
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Basic
    @Column(name = "message_content")
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Basic
    @Column(name = "message_date")
    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    @Basic
    @Column(name = "message_send_ID")
    public String getMessageSendId() {
        return messageSendId;
    }

    public void setMessageSendId(String messageSendId) {
        this.messageSendId = messageSendId;
    }

    @Basic
    @Column(name = "message_receive_ID")
    public String getMessageReceiveId() {
        return messageReceiveId;
    }

    public void setMessageReceiveId(String messageReceiveId) {
        this.messageReceiveId = messageReceiveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UmessageEntity entity = (UmessageEntity) o;

        if (messageId != null ? !messageId.equals(entity.messageId) : entity.messageId != null)
            return false;
        if (messageType != null ? !messageType.equals(entity.messageType) : entity.messageType != null)
            return false;
        if (messageContent != null ? !messageContent.equals(entity.messageContent) : entity.messageContent != null)
            return false;
        if (messageDate != null ? !messageDate.equals(entity.messageDate) : entity.messageDate != null)
            return false;
        if (messageSendId != null ? !messageSendId.equals(entity.messageSendId) : entity.messageSendId != null)
            return false;
        if (messageReceiveId != null ? !messageReceiveId.equals(entity.messageReceiveId) : entity.messageReceiveId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (messageContent != null ? messageContent.hashCode() : 0);
        result = 31 * result + (messageDate != null ? messageDate.hashCode() : 0);
        result = 31 * result + (messageSendId != null ? messageSendId.hashCode() : 0);
        result = 31 * result + (messageReceiveId != null ? messageReceiveId.hashCode() : 0);
        return result;
    }
}
