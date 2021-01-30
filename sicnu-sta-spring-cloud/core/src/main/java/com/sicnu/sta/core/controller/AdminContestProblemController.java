package com.sicnu.sta.core.controller;

import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.service.impl.AdminContestProblemServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminContestProblemController {

    @Resource
    AdminContestProblemServiceImpl adminContestProblemService;

    // 查询比赛下对应类型的题目
    //@MyOpLog(value = "查询比赛的题目")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/query-contest-problem")
    public ResultUtils<Object> queryContestProblem(@RequestParam(value = "contestId") int contestId,
                                                   @RequestParam(value = "typeId") int typeId,
                                                   @RequestParam(value = "page") int page,
                                                   @RequestParam(value = "pageSize") int pageSize) {
        return adminContestProblemService.queryProblemListPaging(contestId, typeId, page, pageSize);
    }

    // 向比赛中添加题目
    //@MyOpLog(value = "向比赛中添加题目")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/add-problem-to-contest")
    public ResultUtils<Object> addProblemToContest(@RequestParam(value = "contestId") Integer contestId,
                                    @RequestParam(value = "problemId") Integer problemId,
                                    @RequestParam(value = "score") Integer score) {
        return adminContestProblemService.addProblemToContest(contestId, problemId, score);
    }

    // 删除比赛中的题目
    //@MyOpLog(value = "删除比赛中的题目")
    @RequiresRoles(value = {"SuperAdmin", "Admin", "Teacher"}, logical = Logical.OR)
    @PostMapping("/delete-problem-to-contest")
    public ResultUtils<Object> deleteProblemToContest(@RequestParam(value = "contestId") Integer contestId,
                                       @RequestParam(value = "problemId") Integer problemId){
        return adminContestProblemService.deleteContestProblem(contestId, problemId);
    }

}
