package com.marcelokmats.lanchonete.orderList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.IngredientUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListPresenterImpl implements OrderListPresenter, Callback<List<Order>> {

    private OrderListView mView;

    private SparseArray<Ingredient> mIngredients;

    private SparseArray<Ingredient> mAllIngredients;

    private SparseArray<Sandwich> mAllSandwiches;


    public OrderListPresenterImpl(OrderListView view) {
        this.mView = view;
    }

    @Override
    public void fetchOrders() {
        Call<List<Order>> call = ApiUtils.getInterface().getOrders();
        call.enqueue(this);
    }

    @Override
    public void fetchAllIngredients() {
        Call<List<Ingredient>> call = ApiUtils.getInterface().getIngredients();
        call.enqueue(new AllIngredientsCallback(this));
    }

    @Override
    public void fetchAllSandwiches() {
        Call<List<Sandwich>> call = ApiUtils.getInterface().getSandwiches();
        call.enqueue(new AllSandwichesCallback(this));
    }

    @Override
    public void ingredientsFetched(List<Ingredient> ingredientList) {
        mAllIngredients = IngredientUtil.transformListIntoSparseArray(ingredientList);

        if (this.mAllSandwiches != null) {
            this.fetchOrders();
        }
    }

    @Override
    public void ingredientsSandwiches(List<Sandwich> sandwichList) {
        mAllSandwiches = new SparseArray<>();

        if (sandwichList != null) {
            for (Sandwich sandwich : sandwichList) {
                mAllSandwiches.put(sandwich.getId(), sandwich);
            }
        }

        if (this.mAllIngredients != null) {
            this.fetchOrders();
        }
    }

    @Override
    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
        if (response != null && response.body() != null) {
            this.mView.setOrderList(response.body(), this.mAllSandwiches, this.mAllIngredients);
        }
    }

    @Override
    public void onFailure(Call<List<Order>> call, Throwable t) {
        Log.e("Lanchonete", "Error while fetching order", t);
    }

}
