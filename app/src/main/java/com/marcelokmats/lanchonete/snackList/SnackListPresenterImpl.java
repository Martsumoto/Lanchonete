package com.marcelokmats.lanchonete.snackList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.util.PriceUtil;
import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnackListPresenterImpl implements SnackListPresenter, Callback<List<Ingredient>> {

    private SnackListView mView;

    private SparseArray<Ingredient> mIngredients;


    public SnackListPresenterImpl(SnackListView view) {
        this.mView = view;
    }


    @Override
    public void fetchIngredients() {
        Call<List<Ingredient>> call = ApiUtils.getInterface().getIngredients();
        call.enqueue(this);
    }

    @Override
    public BigDecimal calculatePrice(List<Ingredient> ingredients) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        totalPrice = PriceUtil.value(ingredients);

        return totalPrice;
    }

    @Override
    public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
        if (response != null && response.body() != null) {
        }
    }

    @Override
    public void onFailure(Call<List<Ingredient>> call, Throwable t) {
        Log.e("Lanchonete", "Error while fetching snacks", t);
    }
}
