package com.sicnu.sta.data.controller;

import com.sicnu.sta.commons.entity.Contest;
import com.sicnu.sta.data.dao.ContestDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contest-dao")
public class ContestDaoController {

    @Resource
    private ContestDao contestDao;

    // 创建题目集
    @PostMapping("/create/contest")
    void createContest(@RequestBody Contest contest) {
        contestDao.createContest(contest);
    }

    // 查询所有题目集列表的 id 和 title
    @GetMapping("/query/contest-list-info")
    List<Map<String, Object>> queryContestListInfo(@RequestParam("cnt") int cnt,
                                                   @RequestParam("pageSize") int pageSize) {
        return contestDao.queryContestListInfo(cnt, pageSize);
    }

    //查询题目集数量
    @GetMapping("/query/contest-cnt")
    int queryContestCnt() {
        return contestDao.queryContestCnt();
    }

    // 查询指定比赛信息
    @GetMapping("/query/contest-info/by-contestid")
    Map<String, Object> queryContestInfoByContestId(@RequestParam("contestId") int contestId) {
        return contestDao.queryContestInfoByContestId(contestId);
    }

    // 查询比赛的开始和结束时间
    @GetMapping("/query/contest-time")
    Map<String, Object> queryContestTime(@RequestParam("contestId") Integer contestId) {
        return contestDao.queryContestTime(contestId);
    }

    // 修改比赛信息
    @PostMapping("/update/contest-info")
    void updateContestInfo(@RequestBody Contest contest) {
        contestDao.updateContestInfo(contest);
    }

    // 给班级添加比赛
    @GetMapping("/add/contest-to-class")
    void addContestToClass(@RequestParam("classId") Integer classId,
                           @RequestParam("contestId") Integer contestId) {
        contestDao.addContestToClass(classId, contestId);
    }

    // 给学生添加比赛
    @GetMapping("/add/contest-to-stu")
    void addContestToStudent(@RequestParam("contestId") Integer contestId,
                             @RequestParam("studentId") Integer studentId) {
        contestDao.addContestToStudent(contestId, studentId);
    }

    // 查询公开题目集
    @GetMapping("/query/open-contest")
    List<Map<String, Object>> queryOpenContest(@RequestParam("cnt") Integer cnt,
                                               @RequestParam("pageSize") Integer pageSize) {
        return contestDao.queryOpenContest(cnt, pageSize);
    }

    // 查询公开题目集的数量
    @GetMapping("/query/open-contest-cnt")
    Integer queryOpenContestCnt() {
        return contestDao.queryOpenContestCnt();
    }

    // 查询我的题目集
    @GetMapping("/query/my-contest")
    List<Map<String, Object>> queryMyContest(@RequestParam("userId") Integer userId,
                                             @RequestParam("cnt") Integer cnt,
                                             @RequestParam("pageSize") Integer pageSize) {
        return contestDao.queryMyContest(userId, cnt, pageSize);
    }

    // 查询我的题目集的数量
    @GetMapping("/query/my-contest-cnt")
    Integer queryMyContestCnt(@RequestParam("userId") Integer userId) {
        return contestDao.queryMyContestCnt(userId);
    }

    // 查询班级下的比赛 id
    @GetMapping("/query/class-contest-id")
    List<Integer> queryClassContestIds(@RequestParam("classId") Integer classId) {
        return contestDao.queryClassContestIds(classId);
    }

    // 查询比赛下我的提交信息
    @GetMapping("/query/contest/my-submit")
    List<Map<String, Object>> queryContestMySubmit(@RequestParam("contestId") Integer contestId,
                                                   @RequestParam("userId") Integer userId,
                                                   @RequestParam("cnt") Integer cnt,
                                                   @RequestParam("pageSize") Integer pageSize) {
        return contestDao.queryContestMySubmit(contestId, userId, cnt, pageSize);
    }

    // 查询比赛我的提交总数
    @GetMapping("/query/conetest/my-submit-cnt")
    Integer queryContestMySubmitCnt(@RequestParam("contestId") Integer contestId,
                                    @RequestParam("userId") Integer userId) {
        return contestDao.queryContestMySubmitCnt(contestId, userId);
    }

    // 查询比赛下所有人的提交信息
    @GetMapping("/query/contest/all-submit")
    List<Map<String, Object>> queryContestAllSubmit(@RequestParam("contestId") Integer contestId,
                                                    @RequestParam("cnt") Integer cnt,
                                                    @RequestParam("pageSize") Integer pageSize) {
        return contestDao.queryContestAllSubmit(contestId, cnt, pageSize);
    }

    // 查询比赛下提交总数
    @GetMapping("/query/contest/all-submit-cnt")
    Integer queryContestAllSubmitCnt(@RequestParam("contestId") Integer contestId) {
        return contestDao.queryContestAllSubmitCnt(contestId);
    }

    // 查询比赛是否可以看榜
    @GetMapping("/query/contest/is-real-rank")
    Integer queryContestIsRealRank(@RequestParam("contestId") Integer contestId) {
        return contestDao.queryContestIsRealRank(contestId);
    }

    // 查询当前比赛下的用户
    @GetMapping("/query/contest/user")
    List<Integer> queryContestUser(@RequestParam("contestId") Integer contestId,
                                   @RequestParam("cnt") Integer cnt,
                                   @RequestParam("pageSize") Integer pageSize) {
        return contestDao.queryContestUser(contestId, cnt, pageSize);
    }

    // 查询当前比赛下的所有用户
    @GetMapping("/query/contest/user-cnt")
    List<Integer> queryContestUserCnt(@RequestParam("contestId") Integer contestId) {
        return contestDao.queryContestUserCnt(contestId);
    }

    // 查询某个人在当前比赛下获得的总分
    @GetMapping("/query/contest/user-sum-score")
    Integer queryContestUserSumScore(@RequestParam("contestId") Integer contestId,
                                     @RequestParam("userId") Integer userId) {
        return contestDao.queryContestUserSumScore(contestId, userId);
    }

    // 查询某班某场比赛下的结果
    @GetMapping("/query/class-contest/user-id")
    List<Integer> queryClassContestUserIds(@RequestParam("contestId") Integer contestId,
                                           @RequestParam("classId") Integer classId) {
        return contestDao.queryClassContestUserIds(contestId, classId);
    }

    // 查询该比赛下的班级 Id
    @GetMapping("/query/class/contest")
    List<Integer> queryClassIdsOfContest(@RequestParam("contestId") Integer contestId) {
        return contestDao.queryClassIdsOfContest(contestId);
    }
}
