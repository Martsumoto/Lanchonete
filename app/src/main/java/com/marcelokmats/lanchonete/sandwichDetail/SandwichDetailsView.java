package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

public interface SandwichDetailsView {

    void setActionBarTitle(String title);
    void showProgressBar(boolean showProgressBar);
    void populateSandwichInfo(Sandwich sandwich, SparseArray<Ingredient> allIngredientList);
    void finishActivity();
}
