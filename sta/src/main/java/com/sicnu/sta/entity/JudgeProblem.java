package com.sicnu.sta.entity;

import java.util.List;

/**
 * 判断题实体类
 */
public class JudgeProblem {

    // 题目 id
    private Integer problemId;
    // 比赛 id
    private Integer contestId;
    // 创建者 id
    private Integer userId;
    // 题目
    private String title;
    // 答案
    private String ans;
    // 分数
    private Integer score;
    // 是否可用
    private Integer available;
    // 题目难度
    private Integer difficulty;
    // 题目标签
    private List<String> tagList;

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    @Override
    public String toString() {
        return "JudgeProblem{" +
                "problemId=" + problemId +
                ", contestId=" + contestId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", ans=" + ans +
                ", score=" + score +
                ", available=" + available +
                ", difficulty=" + difficulty +
                ", tagList=" + tagList +
                '}';
    }
}
