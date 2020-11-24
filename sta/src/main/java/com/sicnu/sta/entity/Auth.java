package com.sicnu.sta.entity;

/**
 * 权限实体类
 */
public class Auth {

    // 权限 id
    private int authId;
    // 权限名
    private String authName;
    // 权限描述
    private String description;
    // 权限指向的 url
    private String authUrl;
    // 父权限
    private int pAuthId;

    public Auth(int authId, String authName, String description, String authUrl, int pAuthId) {
        this.authId = authId;
        this.authName = authName;
        this.description = description;
        this.authUrl = authUrl;
        this.pAuthId = pAuthId;
    }

    public Auth(int authId, String authName, String authUrl, int pAuthId) {
        this.authId = authId;
        this.authName = authName;
        this.authUrl = authUrl;
        this.pAuthId = pAuthId;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
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

    public int getpAuthId() {
        return pAuthId;
    }

    public void setpAuthId(int pAuthId) {
        this.pAuthId = pAuthId;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
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
