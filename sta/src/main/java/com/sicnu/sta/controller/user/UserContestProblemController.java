package com.sicnu.sta.controller.user;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.user.impl.UserContestProblemServiceImpl;
import com.sicnu.sta.service.user.impl.UserContestServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserContestProblemController {

    @Resource
    UserContestServiceImpl userContestService;

    @Resource
    UserContestProblemServiceImpl userContestProblemService;

    // 查询比赛下对应类型的题目
    @MyOpLog(value = "查询比赛下的题目")
    @PostMapping("/user/query-contest-problem")
    public ResultUtils<Object> queryContestProblem(@RequestParam(value = "contestId") Integer contestId,
                                                   @RequestParam(value = "typeId") Integer typeId) {
        return userContestService.queryProblemList(contestId, typeId);
    }

    // 查询上一次的答案
    @MyOpLog(value = "查询上一次的答案")
    @PostMapping("/query-last-answer")
    public ResultUtils<Object> queryLastAnswerOfObjective(@RequestParam(value = "contestId") int contestId,
                                                          @RequestParam(value = "typeId") int typeId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userContestProblemService.queryLastAnswerOfObjective(loginUser.getUserId(), contestId, typeId);
    }

    // 查询编程题信息
    @MyOpLog(value = "查询编程题信息")
    @PostMapping("/query/program-problem")
    public ResultUtils<Object> queryProgramProblem(@RequestParam(value = "problemId") Integer problemId) {
        return userContestProblemService.queryProgramProblem(problemId);
    }

    // 查询比赛下所有类型的题目
    @MyOpLog(value = "查询比赛下所有类型的题目")
    @PostMapping("/query/contest/all-type-problem")
    public ResultUtils<Object> queryContestAllTypeProblem(@RequestParam(value = "contestId") Integer contestId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return userContestProblemService.queryContestAllTypeProblem(contestId, loginUser.getUserId());
    }
}
