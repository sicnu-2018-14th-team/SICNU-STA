package com.sicnu.sta.entity;

/**
 * 所有题目的实体类
 */
public class Problem {

    // 题目 id
    private Integer problemId;
    // 作者 id
    private Integer userId;
    // 难度
    private Integer difficulty;
    // 是否可用
    private Integer available;
    // 题目在具体题目表中的 id
    private Integer realId;
    // 题目类型
    private Integer typeId;
    // 标签 tag[i] = '1' => 该题目拥有一个 id 为 i 的标签
    private String tag;

    public Problem(Integer user_id, Integer difficulty, Integer available, Integer realId, Integer typeId) {
        this.userId = user_id;
        this.difficulty = difficulty;
        this.available = available;
        this.realId = realId;
        this.typeId = typeId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getRealId() {
        return realId;
    }

    public void setRealId(Integer realId) {
        this.realId = realId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUser_id() {
        return userId;
    }

    public void setUser_id(Integer user_id) {
        this.userId = user_id;
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
        return "Problem{" +
                "problemId=" + problemId +
                ", user_id=" + userId +
                ", difficulty=" + difficulty +
                ", available=" + available +
                ", realId=" + realId +
                ", typeId=" + typeId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
