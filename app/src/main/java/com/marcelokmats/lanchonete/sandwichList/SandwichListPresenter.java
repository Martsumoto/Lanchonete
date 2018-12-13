package com.marcelokmats.lanchonete.sandwichList;

import com.marcelokmats.lanchonete.model.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface SandwichListPresenter {

    void fetchSandwiches();

    BigDecimal calculatePrice(List<Ingredient> ingredients);

    void onDestroy();

}
