package org.sajae.application;

public class CommentItem {
    int resId;
    String userId;
    String passTime;
    String comment;
    int recommandCount = 0;

    public CommentItem(int resId, String userId, String passTime, String comment) {
        this.resId = resId;
        this.userId = userId;
        this.passTime = passTime;
        this.comment = comment;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRecommandCount() {
        return recommandCount;
    }

    public void setRecommandCount(int recommandCount) {
        this.recommandCount = recommandCount;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "resId=" + resId +
                ", userId='" + userId + '\'' +
                ", passTime='" + passTime + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
