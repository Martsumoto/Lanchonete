package com.marcelokmats.lanchonete.api;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

public class Repository {

    private static Repository sInstance;

    private SparseArray<Ingredient> mAllIngredients = null;
    private SparseArray<Sandwich> mAllSandwiches = null;

    public static Repository getInstance() {
        if (sInstance == null) {
            sInstance = new Repository();
        }

        return sInstance;
    }

    private Repository() {
    }

    public void setAllIngredients(SparseArray<Ingredient> allIngredients) {
        mAllIngredients = allIngredients;
    }

    public void setAllSandwiches(SparseArray<Sandwich> allSandwiches) {
        mAllSandwiches = allSandwiches;
    }


    public SparseArray<Ingredient> getAllIngredients() {
        return mAllIngredients;
    }

    public SparseArray<Sandwich> getAllSandwiches() {
        return mAllSandwiches;
    }
}
