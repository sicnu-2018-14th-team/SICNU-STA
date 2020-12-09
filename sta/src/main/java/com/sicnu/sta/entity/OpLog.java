package com.sicnu.sta.entity;

/**
 * 操作日志实体类
 */
public class OpLog {

    // 事件 id
    private Integer eventId;
    // 用户 id
    private Integer userId;
    // 用户名
    private String userName;
    // 参数
    private String params;
    // 方法名
    private String method;
    // IP 地址
    private String ip;
    // 操作类型
    private String operation;
    // 操作时间
    private String opTime;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    @Override
    public String toString() {
        return "OpLog{" +
                "eventId=" + eventId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", params='" + params + '\'' +
                ", method='" + method + '\'' +
                ", ip='" + ip + '\'' +
                ", operation='" + operation + '\'' +
                ", opTime='" + opTime + '\'' +
                '}';
    }
}
