package com.sicnu.sta.commons.entity;

import java.util.List;

public class JudgeTask {

    private Integer submitId;

    private List<String> input;

    private List<String> output;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Integer judgeId;

    private String src;

    public JudgeTask() {
    }

    public JudgeTask(Integer submitId, List<String> input, List<String> output, Integer timeLimit, Integer memoryLimit, Integer judgeId, String src) {
        this.submitId = submitId;
        this.input = input;
        this.output = output;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.judgeId = judgeId;
        this.src = src;
    }

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
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

    public Integer getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Integer judgeId) {
        this.judgeId = judgeId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "JudgeTask{" +
                "submitId=" + submitId +
                ", input=" + input +
                ", output=" + output +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", judgeId=" + judgeId +
                ", src='" + src + '\'' +
                '}';
    }
}
