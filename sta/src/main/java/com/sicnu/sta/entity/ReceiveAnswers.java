package com.sicnu.sta.entity;

import java.util.List;

/**
 * 接收前端发送的答案
 */
public class ReceiveAnswers {

    // userId
    private Integer userId;
    // 比赛 id
    private Integer contestId;
    // 题目 id 集合
    private List<Integer> problemIds;
    // 对应题目的答案集合
    private List<String> userAnswers;

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

    public List<Integer> getProblemIds() {
        return problemIds;
    }

    public void setProblemIds(List<Integer> problemIds) {
        this.problemIds = problemIds;
    }

    public List<String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Override
    public String toString() {
        return "ReceiveAnswers{" +
                "userId=" + userId +
                ", contestId=" + contestId +
                ", problemIds=" + problemIds +
                ", userAnswers=" + userAnswers +
                '}';
    }
}
