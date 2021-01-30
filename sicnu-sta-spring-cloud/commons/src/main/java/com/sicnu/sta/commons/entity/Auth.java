package com.sicnu.sta.commons.entity;

/**
 * 权限实体类
 */
public class Auth {

    // 权限 id
    private Integer authId;
    // 权限名
    private String authName;
    // 权限描述
    private String description;
    // 权限指向的 url
    private String authUrl;
    // 父权限
    private int pAuthId;

    public Auth() {

    }

    public Auth(Integer authId, String authName, String description, String authUrl, Integer pAuthId) {
        this.authId = authId;
        this.authName = authName;
        this.description = description;
        this.authUrl = authUrl;
        this.pAuthId = pAuthId;
    }

    public Auth(Integer authId, String authName, String authUrl, Integer pAuthId) {
        this.authId = authId;
        this.authName = authName;
        this.authUrl = authUrl;
        this.pAuthId = pAuthId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public int getpAuthId() {
        return pAuthId;
    }

    public void setpAuthId(int pAuthId) {
        this.pAuthId = pAuthId;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "authId=" + authId +
                ", authName='" + authName + '\'' +
                ", description='" + description + '\'' +
                ", authUrl='" + authUrl + '\'' +
                ", pAuthId=" + pAuthId +
                '}';
    }
}
