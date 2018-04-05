package com.noahkim.androidchallenge.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BadgeCounts {
    @SerializedName("bronze")
    @Expose
    private Integer bronze;

    @SerializedName("silver")
    @Expose
    private Integer silver;

    @SerializedName("gold")
    @Expose
    private Integer gold;

    public Integer getBronze() {
        return bronze;
    }

    public Integer getSilver() {
        return silver;
    }

    public Integer getGold() {
        return gold;
    }

}