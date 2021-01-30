package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.Answer;
import com.sicnu.sta.data.dao.AnswerDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/answer-dao")
public class AnswerDaoController {
    
    @Resource
    private AnswerDao answerDao;

    // 保存用户提交的客观题答案
    @PostMapping("/save/user-answer")
    void saveUserAnswer(@RequestBody Answer answer) {
        answerDao.saveUserAnswer(answer);
    }

    // 查询用户某一道题的答题结果
    @GetMapping("/query/user-objective-result")
    Answer queryUserObjectiveResult(@RequestParam("userId") Integer userId,
                                    @RequestParam("contestId") Integer contestId,
                                    @RequestParam("problemId") Integer problemId) {
        return answerDao.queryUserObjectiveResult(userId, contestId, problemId);
    }

    // 评判客观题
    @GetMapping("/update/user-score-result")
    void updateUserScoreAndResult(@RequestParam("result") String result,
                                  @RequestParam("userScore") Integer userScore,
                                  @RequestParam("userId") Integer userId,
                                  @RequestParam("contestId") Integer contestId,
                                  @RequestParam("problemId") Integer problemId) {
        answerDao.updateUserScoreAndResult(result, userScore, userId, contestId, problemId);
    }

    // 查询之前保存的客观题答案
    @GetMapping("/query/last-answer/of-objective")
    Map<String, Object> queryLastAnswerOfObjective(@RequestParam("contestId") Integer contestId,
                                                   @RequestParam("userId") Integer userId,
                                                   @RequestParam("problemId") Integer problemId) {
        return answerDao.queryLastAnswerOfObjective(contestId, userId, problemId);
    }

    // 更新之前保存的答案
    @PostMapping("/update/last-answer/of-objective")
    void updateLastAnswerOfObjective(@RequestBody Answer answer) {
        answerDao.updateLastAnswerOfObjective(answer);
    }

    // 保存编程题答案
    @PostMapping("/save/program-answer")
    void saveProgramAnswer(@RequestBody Answer answer) {
        answerDao.saveProgramAnswer(answer);
    }

    // 更新编程题答案
    @PostMapping("/update/program-answer")
    void updateProgramAnswer(@RequestBody Answer answer) {
        answerDao.updateProgramAnswer(answer);
    }
}
