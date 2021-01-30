package com.sicnu.sta.core.controller;

import com.sicnu.sta.commons.entity.LoginUser;
import com.sicnu.sta.commons.entity.ReceiveAnswers;
import com.sicnu.sta.commons.entity.SubmitProgram;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.service.impl.UserJudgeServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserJudgeController {

    @Resource
    UserJudgeServiceImpl userJudgeService;

    // 保存用户答案
    //@MyOpLog(value = "保存客观题答案")
    @PostMapping("/save-answers")
    public ResultUtils<Object> saveAnswers(@RequestBody ReceiveAnswers receiveAnswers) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        receiveAnswers.setUserId(loginUser.getUserId());
        return userJudgeService.saveAnswers(receiveAnswers);
    }

    // 查询客观题的评判结果
    //@MyOpLog(value = "查看客观题的评判结果")
    @PostMapping("/query-result-objective")
    public ResultUtils<Object> queryResultOfObjective(@RequestParam(value = "contestId") int contestId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userJudgeService.queryResultOfObjective(loginUser.getUserId(), contestId);
    }

    // 提交编程题代码
    //@MyOpLog(value = "提交编程题代码")
    @PostMapping("/submit-program")
    public ResultUtils<Object> submitProgram(@RequestBody SubmitProgram submitProgram) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        submitProgram.setUserId(loginUser.getUserId());
        return userJudgeService.submitProgramProblem(submitProgram);
    }
}
