package com.sicnu.sta.entity;

/**
 * 路径实体类
 */
public class TestCasePath {

    // 路径 id
    private Integer pathId;
    // 具体路径
    private String uuid;
    // 对应的编程题 id
    private Integer problemId;

    public TestCasePath(Integer pathId, String uuid, Integer problemId) {
        this.pathId = pathId;
        this.uuid = uuid;
        this.problemId = problemId;
    }

    public TestCasePath(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    @Override
    public String toString() {
        return "Path{" +
                "pathId=" + pathId +
                ", uuid='" + uuid + '\'' +
                ", problemId=" + problemId +
                '}';
    }
}
