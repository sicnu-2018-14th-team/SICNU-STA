package com.sicnu.sta.service.user;

import com.sicnu.sta.utils.ResultUtils;

public interface UserContestProblemService {

    /**
     * 查询之前保存的客观题答案
     * @param userId 用户 id
     * @param contestId 比赛 id
     * @param typeId 题目类型 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryLastAnswerOfObjective(int userId, int contestId, int typeId);

    /**
     * 查询编程题信息
     * @param problemId 题目 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryProgramProblem(Integer problemId);

    /**
     * 查询比赛下所有类型的题目
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryContestAllTypeProblem(Integer contestId);
}
