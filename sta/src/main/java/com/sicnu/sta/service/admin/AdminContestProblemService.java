package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.ContestProblem;
import com.sicnu.sta.utils.ResultUtils;

public interface AdminContestProblemService {

    /**
     * 在比赛下创建题目
     * @param contestProblem 比赛题目实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createProblemToContest(ContestProblem contestProblem);

    /**
     * 查询比赛下对应类型的题目
     * @param contestId 比赛 id
     * @param typeId 类型 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryProblemList(int contestId, int typeId);


    /**
     * 在比赛中添加题目
     * @param contestId 比赛 id
     * @param problemId 题目 id
     * @param score 题目分数
     * @return ResultUtils
     */
    ResultUtils<Object> addProblemToContest(Integer contestId, Integer problemId, Integer score);

    /**
     * 删除比赛下的指定题目
     * @param contestId 比赛 id
     * @param problemId 题目 id
     * @return ResultUtils
     */
    ResultUtils<Object> deleteContestProblem(Integer contestId, Integer problemId);
}
