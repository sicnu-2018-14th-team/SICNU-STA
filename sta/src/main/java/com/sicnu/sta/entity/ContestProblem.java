package com.sicnu.sta.entity;


/**
 * 比赛题目实体类
 */
public class ContestProblem {

    // 比赛 id
    private Integer contestId;
    // 题目 id
    private Integer problemId;
    // 题目分数
    private Integer score;

    public ContestProblem(Integer contestId, Integer problemId, Integer score) {
        this.contestId = contestId;
        this.problemId = problemId;
        this.score = score;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ContestProblem{" +
                "contestId=" + contestId +
                ", problemId=" + problemId +
                ", score=" + score +
                '}';
    }
}
