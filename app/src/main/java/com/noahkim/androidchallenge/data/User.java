package com.noahkim.androidchallenge.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    @SerializedName("badge_counts")
    @Expose
    private BadgeCounts badgeCounts;

    @SerializedName("display_name")
    @Expose
    private String username;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @SerializedName("location")
    @Expose
    private String location;

    public User(BadgeCounts badgeCounts, String username, String profileImage, String location) {
        this.badgeCounts = badgeCounts;
        this.username = username;
        this.profileImage = profileImage;
        this.location = location;
    }

    protected User(Parcel in) {
        username = in.readString();
        profileImage = in.readString();
        location = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }
    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(profileImage);
        parcel.writeString(location);
    }
}
