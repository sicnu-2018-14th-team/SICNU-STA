package com.sicnu.sta.service.user.impl;


import com.sicnu.sta.dao.AnswerDao;
import com.sicnu.sta.dao.ContestProblemDao;
import com.sicnu.sta.dao.ProblemDao;
import com.sicnu.sta.entity.Answer;
import com.sicnu.sta.service.user.UserContestProblemService;
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
public class UserContestProblemServiceImpl implements UserContestProblemService {

    @Resource
    ContestProblemDao contestProblemDao;

    @Resource
    AnswerDao answerDao;

    @Resource
    ProblemDao problemDao;

    /**
     * 查询之前保存的客观题答案
     *
     * @param userId    用户 id
     * @param contestId 比赛 id
     * @param typeId    题目类型 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryLastAnswerOfObjective(int userId, int contestId, int typeId) {
        try {
            List<Integer> problemIds = contestProblemDao.queryProblemIdList(contestId, typeId);
            List<Map<String, Object>> data = new ArrayList<>();
            for (Integer it : problemIds) {
                data.add(answerDao.queryLastAnswerOfObjective(contestId, userId, it));
            }
            return ResultUtils.success(data);
        } catch (Exception e) {
            log.error("查询之前保存的客观题答案失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询编程题信息
     *
     * @param problemId 题目 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryProgramProblem(Integer problemId) {
        try {
            Map<String, Object> map = problemDao.queryProgramProblem(problemId);
            return ResultUtils.success(map);
        } catch (Exception e) {
            log.error("查询编程题信息失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询比赛下所有类型的题目
     *
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryContestAllTypeProblem(Integer contestId, Integer userId) {
        try {
            // 所有类型
            List<Integer> typeIds = problemDao.queryAllTypeIdOfProblem();
            List<Object> resData = new ArrayList<>();
            for (Integer it : typeIds) {
                // 查询比赛下该题目类型的题目 id
                List<Integer> problemIds = problemDao.queryProblemIdsByContestIdAndTypeId(contestId, it);
                List<Object> temp = new ArrayList<>();
                for (Integer problemId : problemIds) {
                    Answer answer = answerDao.queryUserObjectiveResult(userId, contestId, problemId);
                    if (answer == null) {
                        answer = new Answer(userId, contestId, problemId, 0);
                    }
                    temp.add(answer);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("total", problemIds.size());
                map.put("data", temp);
                map.put("type", it);
                resData.add(map);
            }
            return ResultUtils.success(resData);
        } catch (Exception e) {
            log.error("查询比赛下所有类型的题目异常", e);
            return ResultUtils.error();
        }
    }

}
