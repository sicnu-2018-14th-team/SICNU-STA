package com.sicnu.sta.controller.admin;

import com.sicnu.sta.log.MyOpLog;
import com.sicnu.sta.service.admin.impl.AdminContestProblemServiceImpl;
import com.sicnu.sta.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminContestProblemController {

    @Resource
    AdminContestProblemServiceImpl adminContestProblemService;

    // 查询比赛下对应类型的题目
    @MyOpLog(value = "查询比赛的题目")
    @PostMapping("/query-contest-problem")
    public ResultUtils<Object> queryContestProblem(@RequestParam(value = "contestId") int contestId,
                                                   @RequestParam(value = "typeId") int typeId,
                                                   @RequestParam(value = "page") int page,
                                                   @RequestParam(value = "pageSize") int pageSize) {
        return adminContestProblemService.queryProblemListPaging(contestId, typeId, page, pageSize);
    }

    // 向比赛中添加题目
    @MyOpLog(value = "向比赛中添加题目")
    @PostMapping("/add-problem-to-contest")
    public ResultUtils<Object> addProblemToContest(@RequestParam(value = "contestId") Integer contestId,
                                    @RequestParam(value = "problemId") Integer problemId,
                                    @RequestParam(value = "score") Integer score) {
        return adminContestProblemService.addProblemToContest(contestId, problemId, score);
    }

    // 删除比赛中的题目
    @MyOpLog(value = "删除比赛中的题目")
    @PostMapping("/delete-problem-to-contest")
    public ResultUtils<Object> deleteProblemToContest(@RequestParam(value = "contestId") Integer contestId,
                                       @RequestParam(value = "problemId") Integer problemId){
        return adminContestProblemService.deleteContestProblem(contestId, problemId);
    }

}
