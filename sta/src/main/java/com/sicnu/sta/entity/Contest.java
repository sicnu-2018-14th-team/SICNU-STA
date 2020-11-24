package com.sicnu.sta.entity;

public class Contest {

    private int contestId;
    private int userId;
    private String title;
    private String description;
    private String password;
    private int isPrivate;
    private String startTime;
    private String endTime;

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

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
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
                '}';
    }


}
