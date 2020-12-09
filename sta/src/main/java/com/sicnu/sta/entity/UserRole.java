package com.sicnu.sta.entity;

/**
 * 用户角色实体类
 */
public class UserRole {

    // 用户 id
    private Integer UserId;
    // 角色 id
    private Integer RoleId;
    // 用户邮箱
    private String email;
    // 用户名
    private String userName;
    // 角色名
    private String roleName;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "UserId=" + UserId +
                ", RoleId=" + RoleId +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
