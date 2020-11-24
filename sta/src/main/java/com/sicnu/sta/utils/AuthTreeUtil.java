package com.sicnu.sta.utils;

import com.sicnu.sta.entity.Auth;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限树
 */
public class AuthTreeUtil {

    private int authId;
    private int pAuthId;
    private String description;
    private String authName;
    private String authUrl;
    private List<Object> children = new ArrayList<>();

    public AuthTreeUtil(int authId, int pAuthId, String description, String authName, String authUrl, Object children) {
        this.authId = authId;
        this.pAuthId = pAuthId;
        this.description = description;
        this.authName = authName;
        this.authUrl = authUrl;
        this.children = (List<Object>) children;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public int getpAuthId() {
        return pAuthId;
    }

    public void setpAuthId(int pAuthId) {
        this.pAuthId = pAuthId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public void add(AuthTreeUtil child) {
        this.children.add(child);
    }

    public void add(Auth child) {
        this.children.add(child);
    }
}
