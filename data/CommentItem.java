package org.sajae.application4.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentItem implements Parcelable {
    public int id;
    public String writer;
    public int movieId;
    //public String writer_image;
    public String time;
    //public int timestamp;
    public float rating;
    public String contents;
    public int recommend;

    public CommentItem(String writer, int movieId, float rating, String contents) {
        this.id = id;
        this.writer = writer;
        this.movieId = movieId;
        //this.writer_image = writer_image;
        this.time = time;
        //this.timestamp = timestamp;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public CommentItem(Parcel src) {
        id = src.readInt();
        writer = src.readString();
        movieId = src.readInt();
        //writer_image = src.readString();
        time = src.readString();
        //timestamp = src.readInt();
        rating = src.readFloat();
        contents = src.readString();
        recommend = src.readInt();
    }

    public static final Creator CREATOR = new Creator() {
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
        dest.writeInt(id);
        dest.writeString(writer);
        dest.writeInt(movieId);
        //dest.writeString(writer_image);
        dest.writeString(time);
        //dest.writeInt(timestamp);
        dest.writeFloat(rating);
        dest.writeString(contents);
        dest.writeInt(recommend);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
