package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.JudgeProblem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JudgeProblemDao {

    // 添加判断题
    void addJudgeProblem(@Param("judgeProblem") JudgeProblem judgeProblem);

    // 修改判断题
    void updateJudgeProblem(@Param("judgeProblem") JudgeProblem judgeProblem);
}
