package com.sicnu.sta.entity;

import java.util.List;

public class JudgeResult {

    private Integer submitId;

    private String globalMsg;

    private List<ResultCase> result;

    public JudgeResult() {
    }

    public JudgeResult(Integer submitId, String globalMsg, List<ResultCase> result) {
        this.submitId = submitId;
        this.globalMsg = globalMsg;
        this.result = result;
    }

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    public String getGlobalMsg() {
        return globalMsg;
    }

    public void setGlobalMsg(String globalMsg) {
        this.globalMsg = globalMsg;
    }

    public List<ResultCase> getResult() {
        return result;
    }

    public void setResult(List<ResultCase> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JudgeResult{" +
                "submitId=" + submitId +
                ", globalMsg='" + globalMsg + '\'' +
                ", result=" + result +
                '}';
    }
}
