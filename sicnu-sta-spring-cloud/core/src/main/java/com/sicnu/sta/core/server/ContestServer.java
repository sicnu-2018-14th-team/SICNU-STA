package com.sicnu.sta.core.server;

import com.sicnu.sta.commons.entity.Contest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("DATA")
public interface ContestServer {

    // 创建题目集
    @PostMapping("/contest-dao/create/contest")
    void createContest(@RequestBody Contest contest);

    // 查询所有题目集列表的 id 和 title
    @GetMapping("/contest-dao/query/contest-list-info")
    List<Map<String, Object>> queryContestListInfo(@RequestParam("cnt") int cnt,
                                                   @RequestParam("pageSize") int pageSize);

    //查询题目集数量
    @GetMapping("/contest-dao/query/contest-cnt")
    int queryContestCnt();

    // 查询指定比赛信息
    @GetMapping("/contest-dao/query/contest-info/by-contestid")
    Map<String, Object> queryContestInfoByContestId(@RequestParam("contestId") int contestId) ;

    // 查询比赛的开始和结束时间
    @GetMapping("/contest-dao/query/contest-time")
    Map<String, Object> queryContestTime(@RequestParam("contestId") Integer contestId);

    // 修改比赛信息
    @PostMapping("/contest-dao/update/contest-info")
    void updateContestInfo(@RequestBody Contest contest);

    // 给班级添加比赛
    @GetMapping("/contest-dao/add/contest-to-class")
    void addContestToClass(@RequestParam("classId") Integer classId,
                           @RequestParam("contestId") Integer contestId);

    // 给学生添加比赛
    @GetMapping("/contest-dao/add/contest-to-stu")
    void addContestToStudent(@RequestParam("contestId") Integer contestId,
                             @RequestParam("studentId") Integer studentId);

    // 查询公开题目集
    @GetMapping("/contest-dao/query/open-contest")
    List<Map<String, Object>> queryOpenContest(@RequestParam("cnt") Integer cnt,
                                               @RequestParam("pageSize") Integer pageSize);

    // 查询公开题目集的数量
    @GetMapping("/contest-dao/query/open-contest-cnt")
    Integer queryOpenContestCnt();

    // 查询我的题目集
    @GetMapping("/contest-dao/query/my-contest")
    List<Map<String, Object>> queryMyContest(@RequestParam("userId") Integer userId,
                                             @RequestParam("cnt") Integer cnt,
                                             @RequestParam("pageSize") Integer pageSize);

    // 查询我的题目集的数量
    @GetMapping("/contest-dao/query/my-contest-cnt")
    Integer queryMyContestCnt(@RequestParam("userId") Integer userId);

    // 查询班级下的比赛 id
    @GetMapping("/contest-dao/query/class-contest-id")
    List<Integer> queryClassContestIds(@RequestParam("classId") Integer classId);

    // 查询比赛下我的提交信息
    @GetMapping("/contest-dao/query/contest/my-submit")
    List<Map<String, Object>> queryContestMySubmit(@RequestParam("contestId") Integer contestId,
                                                   @RequestParam("userId") Integer userId,
                                                   @RequestParam("cnt") Integer cnt,
                                                   @RequestParam("pageSize") Integer pageSize);

    // 查询比赛我的提交总数
    @GetMapping("/contest-dao/query/conetest/my-submit-cnt")
    Integer queryContestMySubmitCnt(@RequestParam("contestId") Integer contestId,
                                    @RequestParam("userId") Integer userId) ;

    // 查询比赛下所有人的提交信息
    @GetMapping("/contest-dao/query/contest/all-submit")
    List<Map<String, Object>> queryContestAllSubmit(@RequestParam("contestId") Integer contestId,
                                                    @RequestParam("cnt") Integer cnt,
                                                    @RequestParam("pageSize") Integer pageSize);

    // 查询比赛下提交总数
    @GetMapping("/contest-dao/query/contest/all-submit-cnt")
    Integer queryContestAllSubmitCnt(@RequestParam("contestId") Integer contestId);

    // 查询比赛是否可以看榜
    @GetMapping("/contest-dao/query/contest/is-real-rank")
    Integer queryContestIsRealRank(@RequestParam("contestId") Integer contestId);

    // 查询当前比赛下的用户
    @GetMapping("/contest-dao/query/contest/user")
    List<Integer> queryContestUser(@RequestParam("contestId") Integer contestId,
                                   @RequestParam("cnt") Integer cnt,
                                   @RequestParam("pageSize") Integer pageSize);

    // 查询当前比赛下的所有用户
    @GetMapping("/contest-dao/query/contest/user-cnt")
    List<Integer> queryContestUserCnt(@RequestParam("contestId") Integer contestId);

    // 查询某个人在当前比赛下获得的总分
    @GetMapping("/contest-dao/query/contest/user-sum-score")
    Integer queryContestUserSumScore(@RequestParam("contestId") Integer contestId,
                                     @RequestParam("userId") Integer userId);

    // 查询某班某场比赛下的结果
    @GetMapping("/contest-dao/query/class-contest/user-id")
    List<Integer> queryClassContestUserIds(@RequestParam("contestId") Integer contestId,
                                           @RequestParam("classId") Integer classId);

    // 查询该比赛下的班级 Id
    @GetMapping("/contest-dao/query/class/contest")
    List<Integer> queryClassIdsOfContest(@RequestParam("contestId") Integer contestId);
}
