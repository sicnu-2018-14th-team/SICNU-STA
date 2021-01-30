package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface AnswerDao {

    // 保存用户提交的客观题答案
    void saveUserAnswer(@Param("answer") Answer answer);

    // 查询用户某一道题的答题结果
    Answer queryUserObjectiveResult(@Param("userId") Integer userId,
                           @Param("contestId") Integer contestId,
                           @Param("problemId") Integer problemId);

    // 评判客观题
    void updateUserScoreAndResult(@Param("result") String result,
                                  @Param("userScore") Integer userScore,
                                  @Param("userId") Integer userId,
                                  @Param("contestId") Integer contestId,
                                  @Param("problemId") Integer problemId);

    // 查询之前保存的客观题答案
    Map<String, Object> queryLastAnswerOfObjective(@Param("contestId") Integer contestId,
                                                   @Param("userId") Integer userId,
                                                   @Param("problemId") Integer problemId);

    // 更新之前保存的答案
    void updateLastAnswerOfObjective(@Param("answer") Answer answer);

    // 保存编程题答案
    void saveProgramAnswer(@Param("answer") Answer answer);

    // 更新编程题答案
    void updateProgramAnswer(@Param("answer") Answer answer);
}
