package com.sicnu.sta.service.user;

import com.sicnu.sta.utils.ResultUtils;
import org.springframework.data.redis.connection.RedisServer;

public interface UserContestService {

    /**
     * 通过比赛 id 来查询比赛的信息
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryContestInfoByContestId(int contestId, Integer userId);

    /**
     * 查询比赛下对应类型的题目
     * @param contestId 比赛 id
     * @param typeId 类型 id
     * @return ResultUtils
     */
    ResultUtils<Object> queryProblemList(int contestId, int typeId);

    /**
     * 查询公开的题目集
     * @return 公开题目集信息
     */
    ResultUtils<Object> queryOpenContest(Integer page, Integer pageSize, Integer userId);

    /**
     * 查询我的题目集信息
     * @param userId 用户 id
     * @return 我的题目集的信息
     */
    ResultUtils<Object> queryMyContest(Integer userId, Integer page, Integer pageSize);

    /**
     * 查看我的提交列表
     * @param contestId 比赛 id
     * @param userId 用户 id
     * @param page 页码
     * @param pageSize 大小
     * @return 提交信息
     */
    ResultUtils<Object> queryMySubmit(Integer contestId, Integer userId, Integer page, Integer pageSize);

    /**
     * 查询所有提交列表
     * @param contestId 比赛 id
     * @param page 页码
     * @param pageSize 页大小
     * @return 提交信息
     */
    ResultUtils<Object> queryAllSubmit(Integer contestId, Integer page, Integer pageSize);

    /**
     * 查看排名列表
     * @param contestId contestId
     * @param page 页码
     * @param pageSize 页面大小
     * @return 排名列表
     */
    ResultUtils<Object> queryContestRank(Integer contestId, Integer page, Integer pageSize, Integer userId);

}
