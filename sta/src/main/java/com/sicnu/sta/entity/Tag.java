package com.sicnu.sta.entity;

/**
 * 比赛是实体类
 */
public class Tag {

    // 标签 id
    private Integer tagId;
    // 标签名
    private String tagName;
    // 题目 problem[i] = '1' => id 为 i 的题目拥有该标签
    private String problem;

    public Tag(String tagName, String problem) {
        this.tagName = tagName;
        this.problem = problem;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", problem='" + problem + '\'' +
                '}';
    }
}
