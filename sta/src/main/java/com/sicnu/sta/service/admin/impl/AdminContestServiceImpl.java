package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.AnswerDao;
import com.sicnu.sta.dao.ContestDao;
import com.sicnu.sta.dao.ContestProblemDao;
import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.entity.Answer;
import com.sicnu.sta.entity.Contest;
import com.sicnu.sta.service.admin.AdminContestService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdminContestServiceImpl implements AdminContestService {

    @Resource
    ContestDao contestDao;

    @Resource
    UserDao userDao;

    @Resource
    ContestProblemDao contestProblemDao;

    @Resource
    AnswerDao answerDao;




    /**
     * 创建比赛
     *
     * @param contest 比赛实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createContest(Contest contest) {
        try {
            contestDao.createContest(contest);
            Integer id = contest.getContestId();
            return ResultUtils.success(id);
        } catch (Exception e) {
            log.error("创建题目集失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询所有题目集列表的 id 和 title
     *
     * @param page 当前页
     * @param pageSize 页面大小
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryContestListInfo(int pageSize, int page) {
        try {
            List<Map<String, Object>> contests = contestDao.queryContestListInfo((page - 1) * pageSize, pageSize);
            for (Map<String, Object> it : contests) {
                String userName = userDao.queryUserNameByUserId(Math.toIntExact((Long) it.get("user_id")));
                it.put("user_name", userName);
            }
            List<Object> data = new ArrayList<>();
            Map<String, Integer> cnt = new HashMap<>();
            cnt.put("total", contestDao.queryContestCnt());
            data.add(cnt);
            data.add(contests);
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询题目集列表失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 修改比赛信息
     *
     * @param contest 比赛实体类
     * @return result
     */
    @Override
    public ResultUtils<Object> updateContestInfo(Contest contest) {
        try {
            contestDao.updateContestInfo(contest);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("修改比赛信息失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 给班级添加比赛
     *
     * @param classId   班级 id
     * @param contestId 比赛 id
     * @return result
     */
    @Override
    public ResultUtils<Object> addContestToClass(Integer classId, Integer contestId) {
        try {
            contestDao.addContestToClass(classId, contestId);
            // 将该比赛绑定给班级里的学生
            List<Integer> studentIds = userDao.queryClassStudents(classId);
            List<Integer> problemIds = contestProblemDao.queryProblemIdsForContest(contestId);
            for (Integer studentId : studentIds) {
                contestDao.addContestToStudent(contestId, studentId);
                for (Integer problemId : problemIds) {
                    Answer answer = answerDao.queryUserObjectiveResult(studentId, contestId, problemId);
                    if (answer == null) {
                        answer = new Answer(studentId, contestId, problemId, 0);
                        // 向数据库中插入学生-比赛-题目的记录
                        answerDao.saveUserAnswer(answer);
                    }
                }
            }
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("给班级添加比赛失败", e);
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 查询比赛结果
     *
     * @param contestId 比赛 id
     * @return 比赛结果
     */
    @Override
    public ResultUtils<Object> queryContestResult(Integer contestId, Integer classId) {
        try {
            List<Integer> userIds = contestDao.queryClassContestUserIds(contestId, classId);
            List<Map<String, Integer>> mapList = new ArrayList<>();
            List<Map<String, Object>> data = new ArrayList<>();
            System.out.println("contestId : " + contestId);
            for (Integer userId : userIds) {
                System.out.println("userId : " + userId);
                int sumScore = contestDao.queryContestUserSumScore(contestId, userId);
                Map<String, Integer> tmp = new HashMap<>();
                tmp.put("userId", userId);
                tmp.put("score", sumScore);
                mapList.add(tmp);
            }
            mapList.sort((o1, o2) -> {
                Integer a = o1.get("score");
                Integer b = o2.get("score");
                return b.compareTo(a);
            });
            int rank = 0;
            int lastScore = -1;
            for (Map<String, Integer> it : mapList) {
                Map<String, Object> map = new HashMap<>();
                int curScore = it.get("score");
                if (curScore != lastScore) rank += 1;
                map.put("rank", rank);
                map.put("score", curScore);
                lastScore = curScore;
                map.put("userName", userDao.queryUserNameByUserId(it.get("userId")));
                data.add(map);
            }
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询比赛信息", e);
            return ResultUtils.error(e.getMessage());
        }
    }
}
