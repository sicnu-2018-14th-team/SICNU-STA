package com.sicnu.sta.entity;


/**
 * 登录日志实体类
 */
public class LoginLog {

    // 登录事件 id
    private Integer eventId;
    // 用户 id
    private Integer userId;
    // 用户名
    private String userName;
    // 用户登录时间
    private String loginTime;
    // 用户登录 IP
    private String ip;
    // 用户登录信息
    private String message;

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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public LoginLog(Integer userId, String userName, String loginTime, String ip, String message) {
        this.userId = userId;
        this.userName = userName;
        this.loginTime = loginTime;
        this.ip = ip;
        this.message = message;
    }

    public LoginLog(String loginTime, String ip, String message) {
        this.loginTime = loginTime;
        this.ip = ip;
        this.message = message;
    }

    public static LoginLog fail(String ip, String loginTime) {
        return new LoginLog(loginTime, ip, "登录失败");
    }

    public static LoginLog success(String ip, User user, String loginTime) {
        return new LoginLog(user.getUserId(), user.getUserName(), loginTime, ip, "登录成功");
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "eventId=" + eventId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", ip='" + ip + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
