package com.sicnu.sta.entity;

import java.util.List;

/**
 * 程序题实体类
 */
public class ProgramProblem {

    // 题目 id => 对应题目表下的 id
    private Integer problemId;
    // 创作则 id
    private Integer userId;
    // 标题
    private String title;
    // 题目描述
    private String description;
    // 输入说明
    private String input;
    // 输出说明
    private String output;
    // 样例
    private String samples;
    // 暗示
    private String hint;
    // 时间限制
    private Integer timeLimit;
    // 空间限制
    private Integer memoryLimit;
    // 题目难度
    private Integer difficulty;
    // 是否可用
    private Integer available;
    // 标签
    private List<String> tagList;
    // 比赛 id
    private Integer contestId;
    // 题目分数
    private Integer score;
    // 路径 id
    private Integer pathId;

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSamples() {
        return samples;
    }

    public void setSamples(String samples) {
        this.samples = samples;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

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

    @Override
    public String toString() {
        return "ProgramProblem{" +
                "problemId=" + problemId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", samples='" + samples + '\'' +
                ", hint='" + hint + '\'' +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", difficulty=" + difficulty +
                ", available=" + available +
                ", tagList=" + tagList +
                '}';
    }
}
