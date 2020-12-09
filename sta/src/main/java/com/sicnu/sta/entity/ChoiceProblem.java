package com.sicnu.sta.entity;

import java.util.List;

/**
 * 判断题
 */
public class ChoiceProblem {

    // 题目 id
    private Integer problemId;
    // 比赛 id
    private Integer contestId;
    // 作者 id
    private Integer userId;
    // 题目分数
    private Integer score;
    // 题目标题
    private String title;
    // A 选项
    private String optionA;
    // B 选项
    private String optionB;
    // C 选项
    private String optionC;
    // D 选项
    private String optionD;
    // 题目答案
    private String ans;
    // 题目是否可用
    private Integer available;
    // 题目难度
    private Integer difficulty;
    // 题目标签
    private List<String> tagList;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "ChoiceProblem{" +
                "problemId=" + problemId +
                ", contestId=" + contestId +
                ", userId=" + userId +
                ", score=" + score +
                ", title='" + title + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", ans='" + ans + '\'' +
                ", available=" + available +
                ", difficulty=" + difficulty +
                ", tagList=" + tagList +
                '}';
    }
}
