package com.marcelokmats.lanchonete.model;

import com.google.gson.annotations.SerializedName;

public class Promotion {

    @SerializedName("id")
    public int mId;

    @SerializedName("name")
    public String mName;

    @SerializedName("description")
    public String mDescription;

}
