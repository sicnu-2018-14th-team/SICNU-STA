package com.sicnu.sta.dao;

import com.sicnu.sta.entity.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface ContestDao {

    // 创建题目集
    void createContest(@Param("contest") Contest contest);

    // 查询所有题目集列表的 id 和 title
    List<Map<String, Object>> queryContestListInfo(@Param("cnt") int cnt,
                                                   @Param("pageSize") int pageSize);

    //查询题目集数量
    int queryContestCnt();

    // 查询指定比赛信息
    Map<String, Object> queryContestInfoByContestId(@Param("contestId") int contestId);

    // 查询比赛的开始和结束时间
    Map<String, Object> queryContestTime(@Param("contestId") Integer contestId);

    // 修改比赛信息
    void updateContestInfo(@Param("contest") Contest contest);

    // 给班级添加比赛
    void addContestToClass(@Param("classId") Integer classId,
                           @Param("contestId") Integer contestId);

    // 给学生添加比赛
    void addContestToStudent(@Param("contestId") Integer contestId,
                             @Param("studentId") Integer studentId);

    // 查询公开题目集
    List<Map<String, Object>> queryOpenContest(@Param("cnt") Integer cnt,
                                               @Param("pageSize") Integer pageSize);

    // 查询公开题目集的数量
    Integer queryOpenContestCnt();

    // 查询我的题目集
    List<Map<String, Object>> queryMyContest(@Param("userId") Integer userId,
                                             @Param("cnt") Integer cnt,
                                             @Param("pageSize") Integer pageSize);

    // 查询我的题目集的数量
    Integer queryMyContestCnt(@Param("userId") Integer userId);

    // 查询班级下的比赛 id
    List<Integer> queryClassContestIds(@Param("classId") Integer classId);

    // 查询比赛下我的提交信息
    List<Map<String, Object>> queryContestMySubmit(@Param("contestId") Integer contestId,
                                                   @Param("userId") Integer userId,
                                                   @Param("cnt") Integer cnt,
                                                   @Param("pageSize") Integer pageSize);

    // 查询比赛我的提交总数
    Integer queryContestMySubmitCnt(@Param("contestId") Integer contestId,
                                    @Param("userId") Integer userId);

    // 查询比赛下所有人的提交信息
    List<Map<String, Object>> queryContestAllSubmit(@Param("contestId") Integer contestId,
                                                    @Param("cnt") Integer cnt,
                                                    @Param("pageSize") Integer pageSize);

    // 查询比赛下提交总数
    Integer queryContestAllSubmitCnt(@Param("contestId") Integer contestId);

    // 查询比赛是否可以看榜
    Integer queryContestIsRealRank(@Param("contestId") Integer contestId);

    // 查询当前比赛下的用户
    List<Integer> queryContestUser(@Param("contestId") Integer contestId,
                                   @Param("cnt") Integer cnt,
                                   @Param("pageSize") Integer pageSize);

    // 查询当前比赛下的所有用户
    List<Integer> queryContestUserCnt(@Param("contestId") Integer contestId);

    // 查询某个人在当前比赛下获得的总分
    Integer queryContestUserSumScore(@Param("contestId") Integer contestId,
                                     @Param("userId") Integer userId);

    // 查询某班某场比赛下的结果
    List<Integer> queryClassContestUserIds(@Param("contestId") Integer contestId,
                                           @Param("classId") Integer classId);

    // 查询该比赛下的班级 Id
    List<Integer> queryClassIdsOfContest(@Param("contestId") Integer contestId);

}
