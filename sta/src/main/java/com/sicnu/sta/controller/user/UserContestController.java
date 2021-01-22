package com.sicnu.sta.controller.user;

import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.user.impl.UserContestServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserContestController {

    @Resource
    UserContestServiceImpl contestService;

    // 通过比赛 id 来查询比赛的信息
    @MyOpLog(value = "查询比赛信息")
    @PostMapping("/query-contest-info")
    public ResultUtils<Object> queryContestInfoByContestId(@RequestParam(value = "contestId") int contestId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return contestService.queryContestInfoByContestId(contestId, loginUser.getUserId());
    }

    // 查询公开题目集
    @MyOpLog(value = "查询公开题目集")
    @PostMapping("/query/open-contest")
    public ResultUtils<Object> queryOpenContest(@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "pageSize") Integer pageSize) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return contestService.queryOpenContest(page, pageSize, loginUser.getUserId());
    }

    // 查询我的题目集
    @MyOpLog(value = "查询我的题目集")
    @PostMapping("/query/my-contest")
    public ResultUtils<Object> queryMyContest(@RequestParam(value = "page") Integer page,
                                              @RequestParam(value = "pageSize") Integer pageSize) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return contestService.queryMyContest(loginUser.getUserId(), page, pageSize);
    }

    // 查看我的提交列表
    @MyOpLog(value = "查看我的提交列表")
    @PostMapping("/query/my-submit")
    public ResultUtils<Object> queryMySubmit(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "pageSize") Integer pageSize,
                                             @RequestParam(value = "contestId") Integer contestId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return contestService.queryMySubmit(contestId, loginUser.getUserId(), page, pageSize);
    }

    // 查看所有的提交列表
    @MyOpLog(value = "查看所有提交列表")
    @PostMapping("/query/all-submit")
    public ResultUtils<Object> queryAllSubmit(@RequestParam(value = "page") Integer page,
                                              @RequestParam(value = "pageSize") Integer pageSize,
                                              @RequestParam(value = "contestId") Integer contestId) {
        return contestService.queryAllSubmit(contestId, page, pageSize);
    }

    // 获取排名列表
    @MyOpLog(value = "获取排名列表")
    @PostMapping("/query/contest-rank")
    public ResultUtils<Object> queryContestRank(@RequestParam(value = "contestId") Integer contestId,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "pageSize") Integer pageSize) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return contestService.queryContestRank(contestId, page, pageSize, loginUser.getUserId());
    }
}
