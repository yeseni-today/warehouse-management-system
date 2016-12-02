package com.repository.model;

/**
 * Created by Finderlo on 2016/11/22.
 */
public class SimpleRes {
    /**
     * 默认的成功信息.
     */
    public static final String SUCCESS = "success";
    /**
     * 默认的失败信息.
     */
    public static final String ERROR = "error";
    /**
     * 默认的成功状态码.
     */
    public static final int SUCCESS_STATUS = 200;
    /**
     * 默认的失败状态码.
     */
    public static final int ERROR_STATUS = 400;


    /**
     * @param object
     * @return 返回一个承载对象的response.
     **/
    public static SimpleRes withObject(final Object object) {
        return new SimpleRes(SUCCESS_STATUS, SUCCESS, object);
    }

    /**
     * @return 返回一个默认成功的response.
     **/
    public static SimpleRes success() {
        return new SimpleRes(SUCCESS_STATUS, SUCCESS);
    }

    /**
     * @return 返回一个默认错误的response.
     **/
    public static SimpleRes error() {
        return new SimpleRes(ERROR_STATUS, ERROR);
    }

    /**
     * @param message error message.
     * @return 返回一个带有错误信息的response.
     **/
    public static SimpleRes error(final String message) {
        return new SimpleRes(ERROR_STATUS, message);
    }

    private int status;

    private String message;

    private Object content;

    public SimpleRes() {
    }

    public SimpleRes(int status, String message) {
        this(status, message, null);
    }

    public SimpleRes(int status, String message, Object content) {
        this.status = status;
        this.content = content;
        this.message = message;
    }

    /**
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     * @return
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     * @return
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param content
     * @return
     */
    public void setContent(Object content) {
        this.content = content;
    }

}
