package com.soil.user.enums;

public enum ErrorCode {
    OK(200,"成功"),
    CREATED(201,"操作成功"),
    NO_CONTENT(204,"删除成功"),
    INVALID_REQUEST(400,"数据错误"),
    Unauthorized(401,"用户或密码错误"),
    Forbidden(403,"用户没有权限"),
    NOT_FOUND(404,"数据不存在"),
    Unprocesable_entity(422,"数据验证错误"),
    INTERNAL_SERVER_ERROR(500,"服务器错误");

    private int code;
    private String msg;

    ErrorCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
