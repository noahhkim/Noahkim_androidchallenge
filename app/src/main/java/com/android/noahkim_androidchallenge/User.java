package com.android.noahkim_androidchallenge;

import java.util.List;

/**
 * Created by noahkim on 4/4/18.
 */

public class User {
    private String username;
    private String gravatar;

    public User(String username, String gravatar) {
        this.username = username;
        this.gravatar = gravatar;
    }

    public String getUsername() {
        return username;
    }

    public String getGravatar() {
        return gravatar;
    }


}
