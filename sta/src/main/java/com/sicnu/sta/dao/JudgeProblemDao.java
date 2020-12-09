package com.sicnu.sta.dao;

import com.sicnu.sta.entity.JudgeProblem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JudgeProblemDao {

    // 添加判断题
    void addJudgeProblem(@Param("judgeProblem") JudgeProblem judgeProblem);
}
