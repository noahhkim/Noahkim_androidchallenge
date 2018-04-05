package com.noahkim.androidchallenge.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by noahkim on 4/4/18.
 */

public class Item {
    @SerializedName("items")
    @Expose
    private List<User> users;

    public Item(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
