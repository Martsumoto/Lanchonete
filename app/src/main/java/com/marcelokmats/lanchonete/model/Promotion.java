package com.marcelokmats.lanchonete.model;

import com.google.gson.annotations.SerializedName;

public class Promotion {

    @SerializedName("id")
    public int mId;

    @SerializedName("name")
    public String mName;

    @SerializedName("description")
    public String mDescription;

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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
