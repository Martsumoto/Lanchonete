package com.marcelokmats.lanchonete.model;

public class CustomIngredient {

    private Integer mAmount;
    private Ingredient mIngredient;

    public CustomIngredient(Integer amount, Ingredient ingredient) {
        mAmount = amount;
        mIngredient = ingredient;
    }

    public Integer getAmount() {
        return mAmount;
    }

    public void setAmount(Integer amount) {
        mAmount = amount;
    }

    public Ingredient getIngredient() {
        return mIngredient;
    }

    public int getIngredientId() {
        int id = 0;

        if (mIngredient != null) {
            id = mIngredient.getId();
        }

        return id;
    }
}
