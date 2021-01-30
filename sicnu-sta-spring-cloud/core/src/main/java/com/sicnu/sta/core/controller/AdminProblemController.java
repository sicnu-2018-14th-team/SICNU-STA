package com.sicnu.sta.core.controller;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.service.impl.AdminProblemServiceImpl;
import com.sicnu.sta.core.service.impl.AdminTagServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class AdminProblemController {

    @Resource
    AdminTagServiceImpl tagService;

    @Resource
    AdminProblemServiceImpl adminProblemService;

    // 根据标签名模糊搜索
    //@MyOpLog(value = "模糊所有标签")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/vague-query/tags")
    public ResultUtils<Object> vagueQueryTags(@RequestParam(value = "tagName") String tagName) {
        return tagService.vagueQueryTags(tagName);
    }

    // 查询题目
    //@MyOpLog(value = "查询题目")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/query-problem-info")
    public ResultUtils<Object> queryProblemInfoByFindProblemClass(@RequestBody FindProblem findProblem) {
        return adminProblemService.queryProblemInfoByFindProblemClass(findProblem);
    }

    // 创建选择题
    //@MyOpLog(value = "创建选择题")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/create/choice-problem")
    public ResultUtils<Object> createChoiceProblem(@RequestBody ChoiceProblem choiceProblem) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        choiceProblem.setUserId(loginUser.getUserId());
        return adminProblemService.createChoiceProblem(choiceProblem);
    }

    // 创建编程题
    @PostMapping("/create/program-problem")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    public ResultUtils<Object> createProgramProblem(@RequestBody ProgramProblem programProblem) {
        programProblem.setUserId(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUserId());
        return adminProblemService.createProgramProblem(programProblem);
    }

    // 上传测试文件
    @PostMapping("/upload/test-case")
    public ResultUtils<Object> uploadTestCases(MultipartFile file) {
        return adminProblemService.unTestCase(file);
    }

}
