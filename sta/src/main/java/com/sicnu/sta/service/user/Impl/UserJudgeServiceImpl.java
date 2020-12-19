package com.sicnu.sta.service.user.Impl;

import com.alibaba.fastjson.JSON;
import com.sicnu.sta.dao.*;
import com.sicnu.sta.entity.*;
import com.sicnu.sta.service.user.UserJudgeService;
import com.sicnu.sta.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sicnu.sta.service.admin.impl.AdminProblemServiceImpl.readTestCase;

@Slf4j
@Service
public class UserJudgeServiceImpl implements UserJudgeService {

    @Resource
    AnswerDao answerDao;

    @Resource
    ContestProblemDao contestProblemDao;

    @Resource
    ProblemDao problemDao;

    @Resource
    UserDao userDao;

    @Resource
    UserServiceImpl userService;

    @Resource
    ContestDao contestDao;


    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    public static final String YES = "答案正确";
    public static final String NO  = "答案错误";

    /**
     * 保存用户提交的客观题答案
     *
     * @param receiveAnswers 用户提交的答案
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> saveAnswers(ReceiveAnswers receiveAnswers) {
        try {
            queryContestIsMyContest(receiveAnswers.getUserId(), receiveAnswers.getContestId());
            int cnt = receiveAnswers.getProblemIds().size();
            Integer userId = receiveAnswers.getUserId();
            Integer contestId = receiveAnswers.getContestId();
            for (int i = 0; i < cnt; i ++) {
                Integer problemId = receiveAnswers.getProblemIds().get(i);
                String userAnswer = receiveAnswers.getUserAnswers().get(i);
                Answer answer = new Answer(userId, contestId, problemId, userAnswer, null, 0);
                /*
                 * 查询之前是否有提交过该题的答案
                 * 如果没有则向数据库中插入
                 * 否则更新数据库中的答案
                 */
                Map<String, Object> map = answerDao.queryLastAnswerOfObjective(contestId, userId, problemId);
                // 如果 map 为空则说明还没提交过答案
                if (map == null) {
                    answerDao.saveUserAnswer(answer);
                } else {
                    answerDao.updateLastAnswerOfObjective(answer);
                }
            }
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("保存用户提交的客观题答案失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 查询客观题答题结果
     *
     * @param userId    用户 id
     * @param contestId 比赛 id
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> queryResultOfObjective(int userId, int contestId) {
        try {
            Map<String, Object> isMyContest = userDao.queryContestIsMyContest(userId, contestId);
            if (isMyContest == null) {
                return ResultUtils.success(-1);
            }
            List<Integer> problemId = contestProblemDao.queryProblemIdsForContest(contestId);
            List<Integer> problemIds  = new ArrayList<>();
            for (int i = 1; i <= 3; i ++) {
                for (Integer it : problemId) {
                    Integer realId = problemDao.queryRealIdByProblemIdAndTypeId(it, i);
                    if (realId != null) {
                        problemIds.add(it);
                    }
                }
            }
            List<Answer> answers = new ArrayList<>();
            // 遍历每一道题
            for (Integer it : problemIds) {
                //Map<String, Integer> map = problemDao.queryProblemRealId(it);
                Integer typeId = problemDao.queryTypeIdByProblemId(it);
                Answer answer = answerDao.queryUserObjectiveResult(userId, contestId, it);
                // 评判结果为空，说明还未评判用户的答案
                   if (answer.getResult() == null) {
                    // typeId 为 3 则为编程题
                    if (typeId == 3) {
                        answer = new Answer(userId, contestId, it, null, NO, 0);
                        answerDao.updateProgramAnswer(answer);
                    } else {
                        // 评判用户的答案
                        judgeObjective(userId, contestId, it, answer.getUserAnswer());
                        answer = answerDao.queryUserObjectiveResult(userId, contestId, it);
                    }
                }
                answers.add(answer);
            }
            return ResultUtils.success(answers);
        } catch (Exception e) {
            log.error("查询客观题答题结果失败", e);
            return ResultUtils.error();
        }
    }

    /**
     * 评判客观题的答案
     *
     * @param userId     用户 id
     * @param contestId  比赛 id
     * @param problemId  题目类型 id
     * @param userAnswer 用户提交的答案
     */
    @Override
    public void judgeObjective(int userId, int contestId, int problemId, String userAnswer) {
        try {
            Map<String, Object> map1 = problemDao.queryRealIdAndTypeIdByProblemId(problemId);
            String tableName = problemDao.queryTableNameByTypeId(Math.toIntExact((Long) map1.get("type_id")));
            Map<String, Object> map2 = problemDao.queryAnswerAndScore(Math.toIntExact((Long) map1.get("real_id")), tableName);
            String standardAnswer = (String) map2.get("ans");
            Integer score = (Integer) map2.get("score");
            if (standardAnswer.equals(userAnswer)) {
                String CORRECT = "答案正确";
                answerDao.updateUserScoreAndResult(CORRECT, score, userId, contestId, problemId);
            } else {
                String ERROR = "答案错误";
                answerDao.updateUserScoreAndResult(ERROR, 0, userId, contestId, problemId);
            }
        } catch (Exception e) {
            log.error("评判客观题答案失败", e);
        }
    }

    /**
     * 提交编程题
     *
     * @param submitProgram 实体类
     * @return ResultUtils
     */
    @Override
    public ResultUtils<Object> submitProgramProblem(SubmitProgram submitProgram) {
        try {
            queryContestIsMyContest(submitProgram.getUserId(), submitProgram.getContestId());
            submitProgram.setSubmitTime(userService.getDateTime());
            problemDao.insertSubmitProgram(submitProgram);
            // 插入提交记录成功
            if (submitProgram.getSubmitId() > 0) {

                // 查询出该编程题的时间限制、空间限制
                Map<String, Integer> map = problemDao.queryLimitFormProgram(submitProgram.getProblemId());
                String uuid = problemDao.queryPathByProblemId(submitProgram.getProblemId());
                Map<String, List<String>> data = readTestCase(UPLOAD_FOLDER + uuid + "/1/");
                if (data == null) return ResultUtils.fail("解析测试文件失败");
                System.out.println(data);
                List<String> input = data.get("input");
                List<String> output = data.get("output");
                Integer timeLimit = map.get("time_limit");
                Integer memoryLimit = map.get("memory_limit") * 1024;
                // 构造请求判题的实体类
                JudgeTask judgeTask = new JudgeTask(submitProgram.getSubmitId(), input, output, timeLimit, memoryLimit, submitProgram.getType(), submitProgram.getCode());
                // 生产判题请求
                kafkaTemplate.send("judge", JSON.toJSONString(judgeTask));

                // 更新最近一次的提交记录
                // 先查找是否之前是否提交过
                Integer lastSubmitId = problemDao.queryLastSubmit(submitProgram.getUserId(), submitProgram.getContestId(), submitProgram.getProblemId());
                // 存在记录就更新，否则插入该记录
                if (lastSubmitId != null) {
                    problemDao.updateLastSubmit(submitProgram.getUserId(), submitProgram.getContestId(), submitProgram.getProblemId(), submitProgram.getSubmitId());
                } else {
                    problemDao.insertLastSubmit(submitProgram.getUserId(), submitProgram.getContestId(), submitProgram.getProblemId(), submitProgram.getSubmitId());
                }

                // 查询判题结果
                JudgeResult judgeResult = null;
                int cnt = 0;
                while (true) {
                    SubmitProgram submitProgramResult = problemDao.querySubmitResult(submitProgram.getSubmitId());
                    if (submitProgramResult.getResult() != null) {
                        // 判题已经结结束
                        judgeResult = JSON.parseObject(submitProgramResult.getResult(), JudgeResult.class);
                        break;
                    }
                    if (cnt >= 1e9) {
                        List<Object> errorSubmit = new ArrayList<>();
                        String theResult = "提交错误";
                        errorSubmit.add(theResult);
                        JudgeResult judgeResult1 = new JudgeResult("系统错误，请重新提交");
                        errorSubmit.add(judgeResult1);
                        return ResultUtils.success(errorSubmit);
                    }
                }
                // flag = 1 代表答案正确
                int flag = 1;
                if (judgeResult == null || judgeResult.getResult() == null) flag = 0;
                else {
                    for (ResultCase resultCase : judgeResult.getResult()) {
                        if (resultCase.getStatus() != 0) {
                            flag = 0;
                            break;
                        }
                    }
                }
                String theResult;
                if (flag == 1) theResult = YES;
                else theResult = NO;
                // 查询之前的答案
                Map<String, Object> bestAnswer = answerDao.queryLastAnswerOfObjective(submitProgram.getContestId(), submitProgram.getUserId(), submitProgram.getProblemId());
                int userScore = 0;
                if (flag == 1) userScore = contestProblemDao.queryScore(submitProgram.getContestId(), submitProgram.getProblemId());
                Answer answer = new Answer(submitProgram.getUserId(), submitProgram.getContestId(), submitProgram.getProblemId(), submitProgram.getCode(), theResult, userScore);
                // 如果是第一次提交该题
                if (bestAnswer == null) {
                    answerDao.saveProgramAnswer(answer);
                } else if((flag == 1) || NO.equals((String) bestAnswer.get("result")) || bestAnswer.get("result") == null) {
                    answerDao.updateProgramAnswer(answer);
                }
                // 提交总数和正确率
                // 如果之前已经正确了，那么提交总数和正确总数不会再增加
                if (bestAnswer == null || NO.equals((String) bestAnswer.get("result"))) {
                    int a = 1, b = 0;
                    // 此次回答正确
                    if (YES.equals(theResult)) {
                        b = 1;
                    }
                    // 更新提交数和正确数
                    contestProblemDao.updateTotalAndCorrect(a, b, submitProgram.getContestId(), submitProgram.getProblemId());
                }
                List<Object> resData = new ArrayList<>();
                resData.add(theResult);
                resData.add(judgeResult);
                return ResultUtils.success(resData);
            } else {
                return ResultUtils.fail("提交失败");
            }
        } catch (Exception e) {
            log.error("提交编程题异常", e);
            return ResultUtils.error();
        }
    }

    // 查询该比赛是否是我的题目集，如果不是就加入
    @Override
    public void queryContestIsMyContest(Integer userId, Integer contestId) {
        Map<String, Object> isMyContest = userDao.queryContestIsMyContest(userId, contestId);
        // 该比赛不在我的题目集下
        if (isMyContest == null) {
            contestDao.addContestToStudent(contestId, userId);
            List<Integer> problemIds = contestProblemDao.queryProblemIdsForContest(contestId);
            for (Integer problemId : problemIds) {
                Answer answer = answerDao.queryUserObjectiveResult(userId, contestId, problemId);
                if (answer == null) {
                    answer = new Answer(userId, contestId, problemId,0);
                    answerDao.saveUserAnswer(answer);
                }
            }
        }
    }
}
