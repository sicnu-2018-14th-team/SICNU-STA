package com.sicnu.sta.commons.utils;

import com.sicnu.sta.commons.entity.Auth;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限树
 */
public class AuthTreeUtils {

    private int authId;
    private int pAuthId;
    private String description;
    private String authName;
    private String authUrl;
    private List<AuthTreeUtils> children;

    public AuthTreeUtils(Auth auth, List<AuthTreeUtils> children) {
        this.authId = auth.getAuthId();
        this.pAuthId = auth.getpAuthId();
        this.description = auth.getDescription();
        this.authName = auth.getAuthName();
        this.authUrl = auth.getAuthUrl();
        this.children = children;
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

    public List<AuthTreeUtils> getChildren() {
        return children;
    }

    public void setChildren(List<AuthTreeUtils> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "AuthTreeUtil{" +
                "authId=" + authId +
                ", pAuthId=" + pAuthId +
                ", description='" + description + '\'' +
                ", authName='" + authName + '\'' +
                ", authUrl='" + authUrl + '\'' +
                ", children=" + children +
                '}';
    }

    public static List<AuthTreeUtils> buildTree(List<AuthTreeUtils> authTreeUtils, int rootId) {

        for (AuthTreeUtils it1 : authTreeUtils) {
            for (AuthTreeUtils it2 : authTreeUtils) {
                // it1 是 it2 的父亲
                if (it1.getAuthId() == it2.getpAuthId()) {
                    it1.getChildren().add(it2);
                }
            }
        }

        List<AuthTreeUtils> res = new ArrayList<>();
        for (AuthTreeUtils it : authTreeUtils) {
            if(it.getpAuthId() == rootId) {
                res.add(it);
            }
        }
        return res;
    }

}

