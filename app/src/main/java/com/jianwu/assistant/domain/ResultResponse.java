package com.jianwu.assistant.domain;

import java.io.Serializable;

public class ResultResponse<T> implements Serializable {
    String status;
    String code;
    String msg;
    T data;

    public ResultResponse(String status, String code, String msg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultResponse<T> success(T date) {
        return new ResultResponse("200", "200", "", date);
    }

    public static <T> ResultResponse<T> success(T date, String msg) {
        return new ResultResponse("200", "200", "", date);
    }

    public static <T> ResultResponse<T> error(String code, String errorMsg) {
        return new ResultResponse("500", code, errorMsg, null);
    }

}
