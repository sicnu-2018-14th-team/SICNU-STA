package com.sicnu.sta.service.admin.impl;

import com.sicnu.sta.dao.*;
import com.sicnu.sta.entity.*;
import com.sicnu.sta.service.admin.AdminJudgeProblemService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AdminJudgeProblemServiceImpl implements AdminJudgeProblemService {

    @Resource
    JudgeProblemDao judgeProblemDao;

    @Resource
    ProblemDao problemDao;

    @Resource
    ContestProblemDao contestProblemDao;

    @Resource
    ContestDao contestDao;

    @Resource
    AnswerDao answerDao;

    @Resource
    AdminTagServiceImpl tagService;

    // 题目类型
    public static final Integer TYPE = 1;

    /**
     * 添加判断题
     *
     * @param judgeProblem 判断题实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> createJudgeProblem(JudgeProblem judgeProblem) {
        try {
            judgeProblemDao.addJudgeProblem(judgeProblem);
            int judgeProblemId = judgeProblem.getProblemId();
            if (judgeProblemId > 0) {
                Problem problem = new Problem(judgeProblem.getUserId(), judgeProblem.getDifficulty(), judgeProblem.getAvailable(), judgeProblemId, TYPE);
                problemDao.addProblem(problem);
                int problemId = problem.getProblemId();
                if (problemId > 0) {
                    // 如果需要向比赛中添加该题目
                    Integer contestId = judgeProblem.getContestId();
                    if (contestId != null) {
                        List<Integer> userIds = contestDao.queryContestUserCnt(contestId);
                        for (Integer userId : userIds) {
                            Answer answer = new Answer(userId, contestId, problemId, 0);
                            answerDao.saveUserAnswer(answer);
                        }
                        ContestProblem contestProblem = new ContestProblem(contestId, problemId, judgeProblem.getScore());
                        contestProblemDao.addProblemToContest(contestProblem);
                    }
                    String tag = tagService.getTagStr(judgeProblem.getTagList(), problemId);
                    problemDao.setProblemTag(tag, problemId);
                    return ResultUtils.success(problemId);
                } else {
                    return ResultUtils.fail();
                }
            } else {
                return ResultUtils.fail();
            }
        } catch (Exception e) {
            log.error("添加题目失败", e);
            return ResultUtils.error();
        }
    }
}
