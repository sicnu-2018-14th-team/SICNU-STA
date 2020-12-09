package com.sicnu.sta.service.user;

import com.sicnu.sta.entity.ReceiveAnswers;
import com.sicnu.sta.entity.SubmitProgram;
import com.sicnu.sta.utils.ResultUtils;

public interface UserJudgeService {

    /**
     * 保存用户提交的客观题答案
     * @param receiveAnswers 用户提交的答案
     * @return ResultUtils
     */
    ResultUtils<Object> saveAnswers(ReceiveAnswers receiveAnswers);

    /**
     * 查询客观题答题结果
     * @param userId 用户 id
     * @param contestId 比赛 id
     * @param typeId 题目类型 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryResultOfObjective(int userId, int contestId, int typeId);

    /**
     * 评判客观题的答案
     * @param userId 用户 id
     * @param contestId 比赛 id
     * @param problemId 题目类型 id
     * @param userAnswer 用户提交的答案
     */
    void judgeObjective(int userId, int contestId, int problemId, String userAnswer);

    /**
     * 提交编程题
     * @param submitProgram 实体类
     * @return ResultUtils
     */
    ResultUtils<Object> submitProgramProblem(SubmitProgram submitProgram);

}
