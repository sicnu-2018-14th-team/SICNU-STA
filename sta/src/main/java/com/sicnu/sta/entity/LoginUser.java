package com.sicnu.sta.entity;

/**
 * 用户登录实体类
 */
public class LoginUser {

    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String userName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
}
