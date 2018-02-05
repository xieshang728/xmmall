package com.xmall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author xies
 * @date 2018/1/22
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> implements Serializable {

    private int status;

    private String msg;

    private T data;

    private RestResponse(int status) {
        this.status = status;
    }

    private RestResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private RestResponse(int status, T data) {
        this.data = data;
        this.status = status;
    }

    private RestResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> RestResponse<T> success() {
        return new RestResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }


    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> RestResponse<T> success(String msg, T data) {
        return new RestResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> RestResponse<T> error(String msg) {
        return new RestResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> RestResponse<T> error() {
        return new RestResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> RestResponse<T> error(int code, String msg) {
        return new RestResponse<T>(code, msg);
    }

}
