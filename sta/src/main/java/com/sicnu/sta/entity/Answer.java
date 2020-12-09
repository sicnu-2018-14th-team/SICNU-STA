package com.sicnu.sta.entity;

/**
 * 用户提交的答案实体类
 */
public class Answer {

    // 答案 id
    private Integer answerId;
    // 用户 id
    private Integer userId;
    // 当前比赛 id
    private Integer contestId;
    // 当前题目 id
    private Integer problemId;
    // 用户提交的答案
    private String userAnswer;
    // 评判结果
    private String result;
    // 用户获得的分数
    private Integer userScore;

    public Integer getAnswerId() {
        return answerId;
    }

    public Answer(Integer userId, Integer contestId, Integer problemId) {
        this.userId = userId;
        this.contestId = contestId;
        this.problemId = problemId;
    }

    public Answer(Integer userId, Integer contestId, Integer problemId, String userAnswer, String result, Integer userScore) {
        this.userId = userId;
        this.contestId = contestId;
        this.problemId = problemId;
        this.userAnswer = userAnswer;
        this.result = result;
        this.userScore = userScore;
    }

    public Answer(Integer userId, Integer contestId, Integer problemId, String userAnswer) {
        this.userId = userId;
        this.contestId = contestId;
        this.problemId = problemId;
        this.userAnswer = userAnswer;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getUser_score() {
        return userScore;
    }

    public void setUser_score(Integer user_score) {
        this.userScore = user_score;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", userId=" + userId +
                ", contestId=" + contestId +
                ", problemId=" + problemId +
                ", userAnswer='" + userAnswer + '\'' +
                ", result='" + result + '\'' +
                ", user_score=" + userScore +
                '}';
    }
}
