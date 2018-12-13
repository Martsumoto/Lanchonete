package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface SandwichDetailsView {

    void setActionBarTitle(String title);
    void showProgressBar(boolean showProgressBar);
    void populateSandwichInfo(Sandwich sandwich, List<Integer> ingredientList, SparseArray<Ingredient> allIngredientList);
    void finishActivity();
}
