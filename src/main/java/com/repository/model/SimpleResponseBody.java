package com.repository.model;

/**
 * Created by Finderlo on 2016/11/22.
 */
public class SimpleResponseBody {

    private int status;

    private String message;

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
}
