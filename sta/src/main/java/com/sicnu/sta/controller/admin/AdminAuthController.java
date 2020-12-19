package com.sicnu.sta.controller.admin;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminAuthServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminAuthController {

    @Resource
    private AdminAuthServiceImpl authService;


    // 查询用户的权限
    @MyOpLog(value = "获取用户的权限")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/user/get/auth")
    public ResultUtils<Object> getAuthByUserId() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return authService.queryUserAuthByUserId(loginUser.getUserId());
    }
}
