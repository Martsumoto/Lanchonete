package com.marcelokmats.lanchonete.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("id_sandwich")
    public int mSandwichId;

    @SerializedName("extras")
    public List<Integer> mIngredients;

    public int getSandwichId() {
        return mSandwichId;
    }

    public void setSandwichId(int sandwichId) {
        mSandwichId = sandwichId;
    }

    public List<Integer> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Integer> ingredients) {
        mIngredients = ingredients;
    }
}
