package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.ContestProblemDao;
import com.sicnu.sta.dao.ProblemDao;
import com.sicnu.sta.dao.UserDao;
import com.sicnu.sta.entity.ContestProblem;
import com.sicnu.sta.service.admin.AdminContestProblemService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
            String tableName = problemDao.queryTableNameByTypeId(typeId);
            List<Map<String, Object>> problems = new ArrayList<>();
            for (Integer it : problemId) {
                Map<String, Object> map = problemDao.queryProblemInfo(it, tableName);
                map.put("user_name", userDao.queryUserNameByUserId(Math.toIntExact((Long) map.get("user_id"))));
                map.put("problem_id", problemDao.queryProblemIdByRealIdAndTypeId(it, typeId));
                problems.add(map);
            }
            return ResultUtils.success(problems);
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
            ContestProblem contestProblem = new ContestProblem(contestId, problemId, score);
            contestProblemDao.addProblemToContest(contestProblem);
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
}
