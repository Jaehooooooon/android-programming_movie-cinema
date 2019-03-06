package org.sajae.application;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentItem implements Parcelable {
    int resId;
    String userId;
    String passTime;
    String comment;
    float userRating = 3;

    public CommentItem(int resId, String userId, String passTime,String comment, float userRating) {
        this.resId = resId;
        this.userId = userId;
        this.passTime = passTime;
        this.comment = comment;
        this.userRating = userRating;
    }

    public CommentItem(Parcel src) {
        resId = src.readInt();
        userId = src.readString();
        passTime = src.readString();
        comment = src.readString();
        userRating = src.readFloat();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CommentItem createFromParcel(Parcel src) {
            return new CommentItem(src);
        }

        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resId);
        dest.writeString(userId);
        dest.writeString(passTime);
        dest.writeString(comment);
        dest.writeFloat(userRating);
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

    public String getPassTime() { return passTime; }

    public void setPassTime(String passTime) { this.passTime = passTime; }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "resId=" + resId +
                ", userId='" + userId + '\'' +
                ", passTime='" + passTime + '\'' +
                ", comment='" + comment + '\'' +
                ", userRating='" + userRating + '\'' +
                '}';
    }
}
