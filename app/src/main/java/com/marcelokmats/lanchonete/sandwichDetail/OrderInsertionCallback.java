package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInsertionCallback implements Callback<Void> {

    private SandwichDetailsPresenter mPresenter;

    public OrderInsertionCallback(SandwichDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        this.mPresenter.insertionSuccessful();
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.e("Lanchonete", "Could not add sandwich into cart", t);
    }
}
