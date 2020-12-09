package com.sicnu.sta.controller.admin;

import com.sicnu.sta.entity.*;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminProblemServiceImpl;
import com.sicnu.sta.service.admin.impl.AdminTagServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
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
    @MyOpLog(value = "模糊所有标签")
    @PostMapping("/vague-query/tags")
    public ResultUtils<Object> vagueQueryTags(@RequestParam(value = "tagName") String tagName) {
        return tagService.vagueQueryTags(tagName);
    }

    // 查询题目
    @MyOpLog(value = "查询题目")
    @PostMapping("/query-problem-info")
    public ResultUtils<Object> queryProblemInfoByFindProblemClass(@RequestBody FindProblem findProblem) {
        return adminProblemService.queryProblemInfoByFindProblemClass(findProblem);
    }

    // 创建选择题
    @MyOpLog(value = "创建选择题")
    @PostMapping("/create/choice-problem")
    public ResultUtils<Object> createChoiceProblem(@RequestBody ChoiceProblem choiceProblem) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        choiceProblem.setUserId(loginUser.getUserId());
        return adminProblemService.createChoiceProblem(choiceProblem);
    }

    // 创建编程题
    @PostMapping("/create/program-problem")
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
