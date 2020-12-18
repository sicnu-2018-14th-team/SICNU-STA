package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.*;
import com.sicnu.sta.entity.Answer;
import com.sicnu.sta.entity.ContestProblem;
import com.sicnu.sta.service.admin.AdminContestProblemService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class AdminContestProblemServiceImpl implements AdminContestProblemService {

    @Resource
    ContestProblemDao contestProblemDao;

    @Resource
    ProblemDao problemDao;

    @Resource
    UserDao userDao;

    @Resource
    ContestDao contestDao;

    @Resource
    AnswerDao answerDao;

    /**
     * 在比赛下创建题目
     *
     * @param contestProblem 比赛题目实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createProblemToContest(ContestProblem contestProblem) {
        try {
            contestProblemDao.addProblemToContest(contestProblem);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("向比赛中添加题目失败", e);
            return ResultUtils.fail();
        }
    }

    /**
     * 查询比赛下对应类型的题目
     *
     * @param contestId 比赛 id
     * @param typeId 类型 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryProblemList(int contestId, int typeId) {
        try {
            List<Integer> problemId;
            if (contestId > 0) {
                problemId = contestProblemDao.queryRealIdList(contestId, typeId);
            } else {
                problemId = problemDao.queryRealIdByTypeId(typeId);
            }
            return ResultUtils.success(queryProblemInfoByRealId(problemId, typeId));
        } catch (Exception e) {
            log.error("查询题目失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 在比赛中添加题目
     * @param contestId 比赛 id
     * @param problemId 题目 id
     * @param score 题目分数
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> addProblemToContest(Integer contestId, Integer problemId, Integer score) {

        try {
            ContestProblem contestProblem = new ContestProblem(contestId, problemId, score, 0, 0);
            contestProblemDao.addProblemToContest(contestProblem);
            // 向比赛中加了题目，那么对应班级的学生需要向数据库中插入做题记录
            // 1. 查询绑定了该场比赛的班级
            List<Integer> classIds = contestDao.queryClassIdsOfContest(contestId);
            for (Integer classId : classIds) {
                // 2. 查询该班下的学生
                List<Integer> studentIds = userDao.queryClassStudents(classId);
                for (Integer studentId : studentIds) {
                    Answer answer = answerDao.queryUserObjectiveResult(studentId, contestId, problemId);
                    if (answer == null) {
                        answer = new Answer(studentId, contestId, problemId,0);
                        answerDao.saveUserAnswer(answer);
                    }
                }
            }
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("在比赛中添加题目失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 删除比赛下的指定题目
     * @param contestId 比赛 id
     * @param problemId 题目 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> deleteContestProblem(Integer contestId, Integer problemId) {
        try {
            contestProblemDao.deleteContestProblem(contestId, problemId);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("删除比赛下的题目失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 分页查询比赛下的题目
     *
     * @param contestId 比赛 id
     * @param typeId    类型 id
     * @param page      当前页
     * @param pageSize  页大小
     * @return 数据
     */
    @Override
    public ResultUtils<Object> queryProblemListPaging(int contestId, int typeId, int page, int pageSize) {
        try {
            List<Integer> problemId;
            int total;
            if (contestId > 0) {
                total = contestProblemDao.queryProblemIdList(contestId, typeId).size();
                problemId = contestProblemDao.queryRealIdListPaging(contestId, typeId, (page - 1) * pageSize, pageSize);
            } else {
                total = problemDao.queryRealIdByTypeId(typeId).size();
                problemId = problemDao.queryRealIdByTypeIdPaging(typeId, (page - 1) * pageSize, pageSize);
            }
            Map<String, Object> data = new HashMap<>();
            data.put("total", total);
            data.put("data", queryProblemInfoByRealId(problemId, typeId));
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询题目失败", e);
            return ResultUtils.error();
        }
    }

    // 查询题目信息通过题目表下的 id 和题目类型 id
    public Object queryProblemInfoByRealId(List<Integer> problemId, Integer typeId) {

        String tableName = problemDao.queryTableNameByTypeId(typeId);
        List<Map<String, Object>> problems = new ArrayList<>();
        for (Integer it : problemId) {
            Map<String, Object> map = problemDao.queryProblemInfo(it, tableName);
            map.put("user_name", userDao.queryUserNameByUserId(Math.toIntExact((Long) map.get("user_id"))));
            map.put("problem_id", problemDao.queryProblemIdByRealIdAndTypeId(it, typeId));
            problems.add(map);
        }
        return problems;
    }
}
