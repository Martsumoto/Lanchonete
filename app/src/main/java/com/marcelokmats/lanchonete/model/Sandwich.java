package com.marcelokmats.lanchonete.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sandwich {

    @SerializedName("id")
    public int mId;

    @SerializedName("name")
    public String mName;

    @SerializedName("ingredients")
    public List<Integer> mIngredients;

    @SerializedName("image")
    public String mImageUrl;

}
