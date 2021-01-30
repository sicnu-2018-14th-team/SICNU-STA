package com.sicnu.sta.data.dao;

import com.sicnu.sta.commons.entity.ContestProblem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContestProblemDao {

    // 向比赛中添加题目
    void addProblemToContest(@Param("contestProblem") ContestProblem contestProblem);

    // 查询比赛下的题目对应题目表的 id
    List<Integer> queryRealIdList(@Param("contestId") int contestId,
                                  @Param("typeId") int typeId);

    // 删除比赛中的题目
    void deleteContestProblem(@Param("contestId") Integer contestId,
                              @Param("problemId") Integer problemId);

    // 查询比赛下对应题目类型的题目的 id
    List<Integer> queryProblemIdList(@Param("contestId") Integer contestId,
                                     @Param("typeId") Integer typeId);

    // 查找比赛下题目的分数
    Integer queryScore(@Param("contestId") Integer contestId,
                       @Param("problemId") Integer problemId);

    // 查询比赛下的题目对应题目表的 id
    List<Integer> queryRealIdListPaging(@Param("contestId") int contestId,
                                        @Param("typeId") int typeId,
                                        @Param("cnt") int cnt,
                                        @Param("pageSize") int pageSize);

    // 更新提交数和正确数
    void updateTotalAndCorrect(@Param("a") int a,
                               @Param("b") int b,
                               @Param("contestId") int contestId,
                               @Param("problemId") int problemId);

    // 查询比赛下的所有题目
    List<Integer> queryProblemIdsForContest(@Param("contestId") Integer contestId);
}
