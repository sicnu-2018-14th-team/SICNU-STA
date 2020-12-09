package com.sicnu.sta.service.user;

import com.sicnu.sta.utils.ResultUtils;

public interface UserContestService {

    /**
     * 通过比赛 id 来查询比赛的信息
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryContestInfoByContestId(int contestId);

    /**
     * 查询比赛下对应类型的题目
     * @param contestId 比赛 id
     * @param typeId 类型 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryProblemList(int contestId, int typeId);
}
