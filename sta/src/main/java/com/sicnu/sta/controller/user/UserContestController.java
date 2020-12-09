package com.sicnu.sta.controller.user;

import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.user.Impl.UserContestServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
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
        return contestService.queryContestInfoByContestId(contestId);
    }
}
