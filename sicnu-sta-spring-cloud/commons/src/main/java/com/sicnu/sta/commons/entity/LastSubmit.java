package com.sicnu.sta.commons.entity;

/**
 * 最近一次提交
 */
public class LastSubmit {

    // 用户 id
    private Integer userId;
    // 比赛 id
    private Integer contestId;
    // 题目 id
    private Integer problemId;
    // 提交 id
    private Integer submitId;

    public LastSubmit() {}


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

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    @Override
    public String toString() {
        return "LastSubmit{" +
                "userId=" + userId +
                ", contestId=" + contestId +
                ", problemId=" + problemId +
                ", submitId=" + submitId +
                '}';
    }
}


