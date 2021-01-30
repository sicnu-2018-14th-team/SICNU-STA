package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.ContestProblem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("DATA")
public interface ContestProblemServer {

    // 向比赛中添加题目
    @PostMapping("/contest-problem-dao/add/problem-to-contest")
    void addProblemToContest(@RequestBody ContestProblem contestProblem);

    // 查询比赛下的题目对应题目表的 id
    @GetMapping("/contest-problem-dao/query/realid-list")
    List<Integer> queryRealIdList(@RequestParam("contestId") int contestId,
                                  @RequestParam("typeId") int typeId);

    // 删除比赛中的题目
    @GetMapping("/contest-problem-dao/delete/contest-problem")
    void deleteContestProblem(@RequestParam("contestId") Integer contestId,
                              @RequestParam("problemId") Integer problemId);

    // 查询比赛下对应题目类型的题目的 id
    @GetMapping("/contest-problem-dao/query/problem-list")
    List<Integer> queryProblemIdList(@RequestParam("contestId") Integer contestId,
                                     @RequestParam("typeId") Integer typeId);

    // 查找比赛下题目的分数
    @GetMapping("/contest-problem-dao/query/score")
    Integer queryScore(@RequestParam("contestId") Integer contestId,
                       @RequestParam("problemId") Integer problemId);

    // 查询比赛下的题目对应题目表的 id
    @GetMapping("/contest-problem-dao/query/realid-list-paging")
    List<Integer> queryRealIdListPaging(@RequestParam("contestId") int contestId,
                                        @RequestParam("typeId") int typeId,
                                        @RequestParam("cnt") int cnt,
                                        @RequestParam("pageSize") int pageSize);

    // 更新提交数和正确数
    @GetMapping("/contest-problem-dao/update/total-and-correct")
    void updateTotalAndCorrect(@RequestParam("a") int a,
                               @RequestParam("b") int b,
                               @RequestParam("contestId") int contestId,
                               @RequestParam("problemId") int problemId);

    // 查询比赛下的所有题目
    @GetMapping("/contest-problem-dao/query/problemid-for-contest")
    List<Integer> queryProblemIdsForContest(@RequestParam("contestId") Integer contestId);
}
