package com.sicnu.sta.core.service.impl;

import com.sicnu.sta.commons.entity.*;
import com.sicnu.sta.commons.utils.ResultUtils;
import com.sicnu.sta.core.server.AnswerServer;
import com.sicnu.sta.core.server.ContestProblemServer;
import com.sicnu.sta.core.server.ContestServer;
import com.sicnu.sta.core.server.ProblemServer;
import com.sicnu.sta.core.service.AdminJudgeProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AdminJudgeProblemServiceImpl implements AdminJudgeProblemService {

    
    @Resource
    private ProblemServer problemServer;


    @Resource
    private ContestProblemServer contestProblemServer;

    @Resource
    private ContestServer contestServer;

    @Resource
    private AnswerServer answerServer;

    @Resource
    private AdminTagServiceImpl tagService;

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
            problemServer.addJudgeProblem(judgeProblem);
            int judgeProblemId = judgeProblem.getProblemId();
            if (judgeProblemId > 0) {
                Problem problem = new Problem(judgeProblem.getUserId(), judgeProblem.getDifficulty(), judgeProblem.getAvailable(), judgeProblemId, TYPE);
                problemServer.addProblem(problem);
                int problemId = problem.getProblemId();
                if (problemId > 0) {
                    // 如果需要向比赛中添加该题目
                    Integer contestId = judgeProblem.getContestId();
                    if (contestId != null) {
                        List<Integer> userIds = contestServer.queryContestUserCnt(contestId);
                        for (Integer userId : userIds) {
                            Answer answer = new Answer(userId, contestId, problemId, 0);
                            answerServer.saveUserAnswer(answer);
                        }
                        ContestProblem contestProblem = new ContestProblem(contestId, problemId, judgeProblem.getScore());
                        contestProblemServer.addProblemToContest(contestProblem);
                    }
                    String tag = tagService.getTagStr(judgeProblem.getTagList(), problemId);
                    problemServer.setProblemTag(tag, problemId);
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
