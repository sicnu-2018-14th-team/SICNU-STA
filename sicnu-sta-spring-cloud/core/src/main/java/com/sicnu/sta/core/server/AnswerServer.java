package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.Answer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("DATA")
public interface AnswerServer {

    // 保存用户提交的客观题答案
    @PostMapping("/answer-dao/save/user-answer")
    void saveUserAnswer(@RequestBody Answer answer);

    // 查询用户某一道题的答题结果
    @GetMapping("/answer-dao/query/user-objective-result")
    Answer queryUserObjectiveResult(@RequestParam("userId") Integer userId,
                                    @RequestParam("contestId") Integer contestId,
                                    @RequestParam("problemId") Integer problemId);

    // 评判客观题
    @GetMapping("/answer-dao/update/user-score-result")
    void updateUserScoreAndResult(@RequestParam("result") String result,
                                  @RequestParam("userScore") Integer userScore,
                                  @RequestParam("userId") Integer userId,
                                  @RequestParam("contestId") Integer contestId,
                                  @RequestParam("problemId") Integer problemId) ;

    // 查询之前保存的客观题答案
    @GetMapping("/answer-dao/query/last-answer/of-objective")
    Map<String, Object> queryLastAnswerOfObjective(@RequestParam("contestId") Integer contestId,
                                                   @RequestParam("userId") Integer userId,
                                                   @RequestParam("problemId") Integer problemId);

    // 更新之前保存的答案
    @PostMapping("/answer-dao/update/last-answer/of-objective")
    void updateLastAnswerOfObjective(@RequestBody Answer answer);

    // 保存编程题答案
    @PostMapping("/answer-dao/save/program-answer")
    void saveProgramAnswer(@RequestBody Answer answer);

    // 更新编程题答案
    @PostMapping("/answer-dao/update/program-answer")
    void updateProgramAnswer(@RequestBody Answer answer);
}
