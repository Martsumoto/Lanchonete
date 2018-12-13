package com.marcelokmats.lanchonete.orderList;

import android.util.Log;

import com.marcelokmats.lanchonete.model.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllIngredientsCallback implements Callback<List<Ingredient>> {

    private OrderListPresenter mPresenter;

    public AllIngredientsCallback(OrderListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
        if (response != null) {
            this.mPresenter.ingredientsFetched(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Ingredient>> call, Throwable t) {
        Log.e("Lanchonete", "Could not fetch all ingredients", t);
    }
}
