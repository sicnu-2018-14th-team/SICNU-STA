package com.sicnu.sta.core.controller;

import com.sicnu.sta.commons.entity.JudgeProblem;
import com.sicnu.sta.commons.entity.LoginUser;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.service.impl.AdminJudgeProblemServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminJudgeProblemController {

    @Resource
    private AdminJudgeProblemServiceImpl judgeProblemService;


    // 创建判断题
    //@MyOpLog(value = "创建判断题")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/create/problem/judgeproblem")
    public ResultUtils<Object> addJudgeProblem(@RequestBody JudgeProblem judgeProblem) {

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        judgeProblem.setUserId(loginUser.getUserId());
        judgeProblem.setAvailable(1);
        return judgeProblemService.createJudgeProblem(judgeProblem);
    }
}
