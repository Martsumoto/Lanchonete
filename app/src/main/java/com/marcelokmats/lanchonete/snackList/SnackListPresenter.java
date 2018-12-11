package com.marcelokmats.lanchonete.snackList;

import com.marcelokmats.lanchonete.model.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface SnackListPresenter {

    void fetchIngredients();

    BigDecimal calculatePrice(List<Ingredient> ingredients);

}
