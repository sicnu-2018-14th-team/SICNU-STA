package com.sicnu.sta.entity;

import java.util.List;

/**
 * 题目搜索
 */
public class FindProblem {

    // 作者 id
    private Integer authorId;
    // 题目难度
    private Integer difficulty;
    // 题目类型
    private Integer typeId;
    // 题目标签
    private List<Integer> tags;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "FindProblem{" +
                "authorId=" + authorId +
                ", difficulty=" + difficulty +
                ", typeId=" + typeId +
                ", tags=" + tags +
                '}';
    }
}
