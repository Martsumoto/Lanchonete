package com.marcelokmats.lanchonete.orderList;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface OrderListView {

    void setPresenter(OrderListPresenter presenter);

    void setOrderList(List<Order> orderList, SparseArray<Sandwich> sandwichList, SparseArray<Ingredient> ingredientList);
}
