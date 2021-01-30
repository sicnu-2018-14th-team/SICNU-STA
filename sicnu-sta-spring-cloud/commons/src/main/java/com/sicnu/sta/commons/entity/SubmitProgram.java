package com.sicnu.sta.commons.entity;

/**
 * 提价程序题实体类
 */
public class SubmitProgram {

    // 提交 id
    private Integer submitId;
    // 用户 id
    private Integer userId;
    // 比赛 id
    private Integer contestId;
    // 题目 id
    private Integer problemId;
    // 代码类型
    private Integer type;
    // 源代码
    private String code;
    // 提交时间
    private String submitTime;
    // 评判结果
    private String result;

    public SubmitProgram() {}

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SubmitProgram{" +
                "submitId=" + submitId +
                ", userId=" + userId +
                ", contestId=" + contestId +
                ", problemId=" + problemId +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", submitTime='" + submitTime + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
