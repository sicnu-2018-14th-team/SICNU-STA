package com.sicnu.sta.commons.entity;

/**
 * 比赛实体类
 */
public class Contest {

    // 比赛 id
    private Integer contestId;
    // 创建比赛者 id
    private Integer userId;
    // 比赛标题
    private String title;
    // 比赛描述
    private String description;
    // 比赛密码
    private String password;
    // 是否公开
    private Integer isPrivate;
    // 比赛开始时间
    private String startTime;
    // 比赛结束时间
    private String endTime;
    // 比赛是否可查看排名
    private Integer realRank;
    // 是否可用
    private Integer available;

    public Contest() {

    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getRealRank() {
        return realRank;
    }

    public void setRealRank(Integer realRank) {
        this.realRank = realRank;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "contestId=" + contestId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", password='" + password + '\'' +
                ", isPrivate=" + isPrivate +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", realRank=" + realRank +
                ", available=" + available +
                '}';
    }
}
