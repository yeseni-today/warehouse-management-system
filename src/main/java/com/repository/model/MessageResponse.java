package com.repository.model;

/**
 * Created by Finderlo on 2016/11/26.
 */
public class MessageResponse {
    private int status;

    private String message;

    public MessageResponse() {
    }

    public MessageResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static MessageResponse success() {
        return new MessageResponse(200, "success");
    }

    public static MessageResponse error() {
        return new MessageResponse(400, "eoor");
    }

    public static MessageResponse error(String message) {
        return new MessageResponse(400, message);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
