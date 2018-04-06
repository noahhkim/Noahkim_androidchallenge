package com.noahkim.androidchallenge.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BadgeCounts implements Parcelable {
    @SerializedName("bronze")
    @Expose
    private Integer bronze;

    @SerializedName("silver")
    @Expose
    private Integer silver;

    @SerializedName("gold")
    @Expose
    private Integer gold;

    public BadgeCounts(Integer bronze, Integer silver, Integer gold) {
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
    }

    private BadgeCounts(Parcel in) {
        if (in.readByte() == 0) {
            bronze = null;
        } else {
            bronze = in.readInt();
        }
        if (in.readByte() == 0) {
            silver = null;
        } else {
            silver = in.readInt();
        }
        if (in.readByte() == 0) {
            gold = null;
        } else {
            gold = in.readInt();
        }
    }

    public static final Creator<BadgeCounts> CREATOR = new Creator<BadgeCounts>() {
        @Override
        public BadgeCounts createFromParcel(Parcel in) {
            return new BadgeCounts(in);
        }

        @Override
        public BadgeCounts[] newArray(int size) {
            return new BadgeCounts[size];
        }
    };

    public Integer getBronze() {
        return bronze;
    }

    public Integer getSilver() {
        return silver;
    }

    public Integer getGold() {
        return gold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (bronze == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(bronze);
        }
        if (silver == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(silver);
        }
        if (gold == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(gold);
        }
    }
}