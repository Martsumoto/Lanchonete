package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface SandwichDetailsPresenter {

    void fetchSandwichIngredients();
    void setSandwich(Sandwich sandwich);
    void insertSandwich();
    void updateCustomizedSandwich(List<Integer>customIngredientsId);
    void onDestroy();

    SparseArray<Ingredient> getAllIngredients();
    List<Integer> getCustomIngredients();
    Sandwich getSandwich();
}
