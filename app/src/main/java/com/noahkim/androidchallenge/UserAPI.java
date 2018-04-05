package com.noahkim.androidchallenge;

import com.noahkim.androidchallenge.data.Item;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserAPI {
    @GET("2.2/users?site=stackoverflow")
    Call<Item> getItems();
}
