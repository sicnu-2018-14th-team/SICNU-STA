package com.sicnu.sta.controller.admin;

import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.entity.LoginUser;
import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminContestProblemServiceImpl;
import com.sicnu.sta.service.admin.impl.AdminContestServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.apache.kafka.common.security.auth.Login;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminContestController {


    @Resource
    private AdminContestServiceImpl adminContestService;

    // 创建比赛
    @MyOpLog(value = "创建比赛")
    @PostMapping("/create/contest")
    public ResultUtils<Object> createContest(@RequestBody Contest contest) {
        // 获取当前登录用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        contest.setUserId(loginUser.getUserId());
        contest.setAvailable(1);
        return adminContestService.createContest(contest);
    }


    // 查询所有题目集列表信息
    @MyOpLog(value = "查询比赛列表信息")
    @PostMapping("/query/contest/list")
    public ResultUtils<Object> queryContestListInfo(@RequestParam(value = "pageSize") int pageSize,
                                                    @RequestParam(value = "page") int page) {
        return adminContestService.queryContestListInfo(pageSize, page);
    }

    // 修改比赛信息
    @MyOpLog(value = "修改比赛信息")
    @PostMapping("/update/contest-info")
    public ResultUtils<Object> updateContestInfo(@RequestBody Contest contest) {
        return adminContestService.updateContestInfo(contest);
    }

    // 给班级添加比赛
    @MyOpLog(value = "给班级添加比赛")
    @PostMapping("/add-contest-to-class")
    public ResultUtils<Object> addContestToClass(@RequestParam(value = "contestId") Integer contestId,
                                                 @RequestParam(value = "classId") Integer classId) {
        return adminContestService.addContestToClass(classId, contestId);
    }

    // 导出比赛结果
    @MyOpLog(value = "导出比赛结果")
    @PostMapping("/query/contest-result")
    public ResultUtils<Object> queryContestResult(@RequestParam(value = "contestId") Integer contestId,
                                                  @RequestParam(value = "classId") Integer classId) {
        return adminContestService.queryContestResult(contestId, classId);
    }
}
