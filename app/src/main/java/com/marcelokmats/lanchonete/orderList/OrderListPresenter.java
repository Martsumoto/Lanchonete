package com.marcelokmats.lanchonete.orderList;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface OrderListPresenter {

    void fetchOrders();

    void fetchAllIngredients();

    void fetchAllSandwiches();

    void ingredientsFetched(List<Ingredient> ingredientList);

    void ingredientsSandwiches(List<Sandwich> sandwichList);
}
