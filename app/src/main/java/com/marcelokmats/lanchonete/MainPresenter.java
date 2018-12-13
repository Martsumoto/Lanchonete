package com.marcelokmats.lanchonete;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface MainPresenter {

    void ingredientsFetched(List<Ingredient> ingredients);

    void sandwichesFetched(List<Sandwich> sandwiches);

    void prefetchData();

    void onDestroy();
}
