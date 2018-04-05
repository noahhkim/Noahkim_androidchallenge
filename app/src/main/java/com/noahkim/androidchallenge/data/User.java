package com.noahkim.androidchallenge.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("badge_counts")
    @Expose
    private BadgeCounts badgeCounts;

    @SerializedName("display_name")
    @Expose
    private String username;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public User(BadgeCounts badgeCounts, String username, String profileImage) {
        this.badgeCounts = badgeCounts;
        this.username = username;
        this.profileImage = profileImage;
    }

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }
    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
