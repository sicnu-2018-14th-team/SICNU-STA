package com.sicnu.sta.service.admin;

import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.utils.ResultUtils;


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
}
