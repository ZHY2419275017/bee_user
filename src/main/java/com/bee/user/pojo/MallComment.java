package com.bee.user.pojo;

import java.util.Date;

public class MallComment {
    private Integer id;

    private Integer comUserId;

    private Integer comProId;

    private String comTitle;

    private String comContent;

    private Date comTime;

    private Integer comScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComUserId() {
        return comUserId;
    }

    public void setComUserId(Integer comUserId) {
        this.comUserId = comUserId;
    }

    public Integer getComProId() {
        return comProId;
    }

    public void setComProId(Integer comProId) {
        this.comProId = comProId;
    }

    public String getComTitle() {
        return comTitle;
    }

    public void setComTitle(String comTitle) {
        this.comTitle = comTitle == null ? null : comTitle.trim();
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent == null ? null : comContent.trim();
    }

    public Date getComTime() {
        return comTime;
    }

    public void setComTime(Date comTime) {
        this.comTime = comTime;
    }

    public Integer getComScore() {
        return comScore;
    }

    public void setComScore(Integer comScore) {
        this.comScore = comScore;
    }
}