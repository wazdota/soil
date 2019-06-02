package com.soil.gateway.response;

import com.soil.gateway.enums.ErrorCode;

public class ApiResult<T> {

    private int code;

    private String message;

    private T value;

    public ApiResult(int code, String message, T value){
        super();
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public ApiResult(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ApiResult(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ApiResult(ErrorCode errorCode, T value){

        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
