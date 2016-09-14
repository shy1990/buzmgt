package com.wangge.buzmgt.superposition.pojo;

import java.util.Date;

/**
 * Created by joe on 16-9-13.
 * 进程展示页面
 */
public class SalesmanDetails {

    private String userId;//业务员id

    private String userName;//名字

    private String regionId;//区域id

    private Integer taskOne;//任务量一

    private Integer taskTwo;///任务量二

    private Integer taskThree;//任务量三

    private String groupName;

    private Integer nums;//完成量

    private Date startTime;

    private Date endTime;

    public SalesmanDetails() {
    }

    public SalesmanDetails(String userId, String userName, String regionId) {
        this.userId = userId;
        this.userName = userName;
        this.regionId = regionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Integer getTaskOne() {
        return taskOne;
    }

    public void setTaskOne(Integer taskOne) {
        this.taskOne = taskOne;
    }

    public Integer getTaskTwo() {
        return taskTwo;
    }

    public void setTaskTwo(Integer taskTwo) {
        this.taskTwo = taskTwo;
    }

    public Integer getTaskThree() {
        return taskThree;
    }

    public void setTaskThree(Integer taskThree) {
        this.taskThree = taskThree;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SalesmanDetails{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", regionId='" + regionId + '\'' +
                ", taskOne=" + taskOne +
                ", taskTwo=" + taskTwo +
                ", taskThree=" + taskThree +
                ", groupName='" + groupName + '\'' +
                ", nums=" + nums +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
