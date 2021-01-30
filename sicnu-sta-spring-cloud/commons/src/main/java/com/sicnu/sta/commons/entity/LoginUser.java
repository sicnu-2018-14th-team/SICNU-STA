package com.sicnu.sta.commons.entity;

/**
 * 用户登录实体类
 */
public class LoginUser {

    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String userName;
    private String role;

    public LoginUser() {

    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
