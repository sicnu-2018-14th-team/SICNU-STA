package com.sicnu.sta.dao;

import com.sicnu.sta.entity.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContestDao {

    /**
     * 创建题目集
     * @param contest 实体类
     */
    void createContest(@Param("contest") Contest contest);

    /**
     * 查询所有题目集列表的 id 和 title
     * @return
     */
    List<Map<String, Object>> queryContestListInfo(@Param("cnt") int cnt, @Param("pageSize") int pageSize);

    /**
     * 查询题目集数量
     * @return
     */
    int queryContestCnt();
}
