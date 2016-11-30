package com.repository.model;

/**
 * Created by Finderlo on 2016/11/22.
 */
public class SimpleResponse {

    private int status = 200;

    private String message = "success";

    private Object content;

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

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static SimpleResponse withObject(Object object){
        SimpleResponse response = new SimpleResponse();
        response.setContent(object);
        return response;
    }
}
