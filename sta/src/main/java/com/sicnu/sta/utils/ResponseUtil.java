package com.sicnu.sta.utils;

public class ResponseUtil<T> {

    private int status;
    private String message;
    private T data;

    public ResponseUtil() {

    }

    public ResponseUtil(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseUtil(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseUtil{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
