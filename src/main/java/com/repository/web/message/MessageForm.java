package com.repository.web.message;

/**
 * Created by Finderlo on 2016/11/30.
 */
public class MessageForm {

    private String send_ID;

    private String receive_ID;

    private String messageTitle;

    private String messageContent;

    public String getSend_ID() {
        return send_ID;
    }

    public void setSend_ID(String send_ID) {
        this.send_ID = send_ID;
    }

    public String getReceive_ID() {
        return receive_ID;
    }

    public void setReceive_ID(String receive_ID) {
        this.receive_ID = receive_ID;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
