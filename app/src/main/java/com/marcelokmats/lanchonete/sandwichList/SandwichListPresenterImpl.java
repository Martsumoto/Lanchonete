package com.marcelokmats.lanchonete.sandwichList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.PriceUtil;
import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SandwichListPresenterImpl implements SandwichListPresenter, Callback<List<Sandwich>> {

    private SandwichListView mView;

    private SparseArray<Ingredient> mIngredients;

    public static final String SANDWICH = "SANDWICH";


    public SandwichListPresenterImpl(SandwichListView view) {
        this.mView = view;
    }

    @Override
    public void fetchSandwiches() {
        Call<List<Sandwich>> call = ApiUtils.getInterface().getSandwiches();
        call.enqueue(this);
    }

    @Override
    public BigDecimal calculatePrice(List<Ingredient> ingredients) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        totalPrice = PriceUtil.value(ingredients);

        return totalPrice;
    }

    @Override
    public void onResponse(Call<List<Sandwich>> call, Response<List<Sandwich>> response) {
        if (response != null && response.body() != null) {
            this.mView.setSandwichList(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Sandwich>> call, Throwable t) {
        Log.e("Lanchonete", "Error while fetching sandwiches", t);
    }
}
