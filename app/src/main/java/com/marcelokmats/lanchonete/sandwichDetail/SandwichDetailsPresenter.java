package com.marcelokmats.lanchonete.sandwichDetail;

import com.marcelokmats.lanchonete.model.Sandwich;

public interface SandwichDetailsPresenter {

    void fetchSandwichIngredients();
    void setSandwich(Sandwich sandwich);
    void insertSandwich();
    void insertionSuccessful();
}
