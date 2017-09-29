package com.reigndesign.articles.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class Article implements Parcelable {

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    @SerializedName("objectID")
    private String Id;
    @SerializedName(value = "story_title", alternate = {"title"})
    private String Title;
    @SerializedName("created_at_i")
    private long CreateAt;
    @SerializedName("story_url")
    private String Url;
    @SerializedName("author")
    private String Author;
    private boolean IsDisable;

    public Article(String objectId, String storyTitle, long createAt, String url, boolean IsDisable, String author) {
        this.Id = objectId;
        this.Title = storyTitle;
        this.CreateAt = createAt;
        this.Url = url;
        this.IsDisable = IsDisable;
        this.Author = author;
    }

    protected Article(Parcel in) {
        Id = in.readString();
        Title = in.readString();
        CreateAt = in.readLong();
        Url = in.readString();
        IsDisable = in.readInt() == 1;
        Author = in.readString();
    }

    public void setURL(String url) {
        this.Url = url;
    }

    public void setDisable(boolean IsDisable) {
        this.IsDisable = IsDisable;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getUrl() {
        return Url;
    }

    public long getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(long createAt) {
        this.CreateAt = createAt;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public boolean getIsDisable() {
        return IsDisable;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Title);
        parcel.writeLong(CreateAt);
        parcel.writeString(Url);
        parcel.writeInt(IsDisable ? 1 : 0);
        parcel.writeString(Author);
    }
}
