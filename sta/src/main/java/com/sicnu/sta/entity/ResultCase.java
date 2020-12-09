package com.sicnu.sta.entity;

public class ResultCase {

    private Integer status;

    private Integer timeUsed;

    private Integer memoryUsed;

    private String errorMessage;

    private String msg;

    public ResultCase() {
    }

    public ResultCase(Integer status, Integer timeUsed, Integer memoryUsed, String errorMessage, String msg) {
        this.status = status;
        this.timeUsed = timeUsed;
        this.memoryUsed = memoryUsed;
        this.errorMessage = errorMessage;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ResultCase{" +
                "status=" + status +
                ", timeUsed=" + timeUsed +
                ", memoryUsed=" + memoryUsed +
                ", errorMessage='" + errorMessage + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

