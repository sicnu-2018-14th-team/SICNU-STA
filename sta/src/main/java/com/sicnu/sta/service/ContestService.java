package com.sicnu.sta.service;

import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.utils.ResponseUtil;


public interface ContestService {

    /**
     * 创建题目集
     * @param contest 实体类
     */
    ResponseUtil createContest(Contest contest);

    /**
     * 查询所有题目集列表的 id 和 title
     * @return
     */
    ResponseUtil queryContestListInfo(int pageSize, int page);
}
