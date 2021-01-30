package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.ContestProblem;
import com.sicnu.sta.data.dao.ContestProblemDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/contest-problem-dao")
public class ContestProblemDaoController {

    @Resource
    private ContestProblemDao contestProblemDao;

    // 向比赛中添加题目
    @PostMapping("/add/problem-to-contest")
    void addProblemToContest(@RequestBody ContestProblem contestProblem) {
        contestProblemDao.addProblemToContest(contestProblem);
    }

    // 查询比赛下的题目对应题目表的 id
    @GetMapping("/query/realid-list")
    List<Integer> queryRealIdList(@RequestParam("contestId") int contestId,
                                  @RequestParam("typeId") int typeId) {
        return contestProblemDao.queryRealIdList(contestId, typeId);
    }

    // 删除比赛中的题目
    @GetMapping("/delete/contest-problem")
    void deleteContestProblem(@RequestParam("contestId") Integer contestId,
                              @RequestParam("problemId") Integer problemId) {
        contestProblemDao.deleteContestProblem(contestId, problemId);
    }

    // 查询比赛下对应题目类型的题目的 id
    @GetMapping("/query/problem-list")
    List<Integer> queryProblemIdList(@RequestParam("contestId") Integer contestId,
                                     @RequestParam("typeId") Integer typeId) {
        return contestProblemDao.queryProblemIdList(contestId, typeId);
    }

    // 查找比赛下题目的分数
    @GetMapping("/query/score")
    Integer queryScore(@RequestParam("contestId") Integer contestId,
                       @RequestParam("problemId") Integer problemId) {
        return contestProblemDao.queryScore(contestId, problemId);
    }

    // 查询比赛下的题目对应题目表的 id
    @GetMapping("/query/realid-list-paging")
    List<Integer> queryRealIdListPaging(@RequestParam("contestId") int contestId,
                                        @RequestParam("typeId") int typeId,
                                        @RequestParam("cnt") int cnt,
                                        @RequestParam("pageSize") int pageSize) {
        return contestProblemDao.queryRealIdListPaging(contestId, typeId, cnt, pageSize);
    }

    // 更新提交数和正确数
    @GetMapping("/update/total-and-correct")
    void updateTotalAndCorrect(@RequestParam("a") int a,
                               @RequestParam("b") int b,
                               @RequestParam("contestId") int contestId,
                               @RequestParam("problemId") int problemId) {
        contestProblemDao.updateTotalAndCorrect(a, b, contestId, problemId);
    }

    // 查询比赛下的所有题目
    @GetMapping("/query/problemid-for-contest")
    List<Integer> queryProblemIdsForContest(@RequestParam("contestId") Integer contestId) {
        return contestProblemDao.queryProblemIdsForContest(contestId);
    }
}
