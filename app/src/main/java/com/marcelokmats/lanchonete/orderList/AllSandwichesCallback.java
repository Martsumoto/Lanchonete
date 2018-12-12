package com.marcelokmats.lanchonete.orderList;

import android.util.Log;

import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSandwichesCallback implements Callback<List<Sandwich>> {

    private OrderListPresenter mPresenter;

    public AllSandwichesCallback(OrderListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResponse(Call<List<Sandwich>> call, Response<List<Sandwich>> response) {
        if (response != null) {
            this.mPresenter.ingredientsSandwiches(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Sandwich>> call, Throwable t) {
        Log.e("Lanchonete", "Could not fetch all sandwiches", t);
    }
}
