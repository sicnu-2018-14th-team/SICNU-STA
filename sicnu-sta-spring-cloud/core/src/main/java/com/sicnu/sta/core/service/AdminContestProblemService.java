package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.entity.ContestProblem;
import com.sicnu.sta.commons.utils.ResultUtils;

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

    /**
     * 分页查询比赛下的题目
     * @param contestId 比赛 id
     * @param typeId 类型 id
     * @param page 当前页
     * @param pageSize 页大小
     * @return 数据
     */
    ResultUtils<Object> queryProblemListPaging(int contestId, int typeId, int page, int pageSize);
}
