package com.marcelokmats.lanchonete.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sandwich implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    public Sandwich(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = new ArrayList<>();
        in.readList(this.mIngredients, null);
        this.mImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeList(mIngredients);
        dest.writeString(mImageUrl);
    }

    public static final Parcelable.Creator<Sandwich> CREATOR = new Parcelable.Creator<Sandwich>() {

        @Override
        public Sandwich createFromParcel(Parcel source) {
            return new Sandwich(source);
        }

        @Override
        public Sandwich[] newArray(int size) {
            return new Sandwich[size];
        }
    };

    @SerializedName("id")
    public int mId;

    @SerializedName("name")
    public String mName;

    @SerializedName("ingredients")
    public List<Integer> mIngredients;

    @SerializedName("image")
    public String mImageUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Integer> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Integer> ingredients) {
        mIngredients = ingredients;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
