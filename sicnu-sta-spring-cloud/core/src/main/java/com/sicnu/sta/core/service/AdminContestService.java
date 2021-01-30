package com.sicnu.sta.core.service;

import com.sicnu.sta.commons.entity.Contest;
import com.sicnu.sta.commons.utils.ResultUtils;


public interface AdminContestService {

    /**
     * 创建比赛
     * @param contest 比赛实体类
     * @return ResultUtils
     */
    ResultUtils<Object> createContest(Contest contest);

    /**
     * 查询所有题目集列表的 id 和 title
     * @param page 当前页
     * @param pageSize 页面大小
     * @return ResultUtils
     */
    ResultUtils<Object> queryContestListInfo(int pageSize, int page);

    /**
     * 修改比赛信息
     * @param contest 比赛实体类
     * @return result
     */
    ResultUtils<Object> updateContestInfo(Contest contest);

    /**
     * 给班级添加比赛
     * @param classId 班级 id
     * @param contestId 比赛 id
     * @return result
     */
    ResultUtils<Object> addContestToClass(Integer classId, Integer contestId);

    /**
     * 查询比赛结果
     * @param contestId 比赛 id
     * @return 比赛结果
     */
    ResultUtils<Object> queryContestResult(Integer contestId, Integer classId);
}
