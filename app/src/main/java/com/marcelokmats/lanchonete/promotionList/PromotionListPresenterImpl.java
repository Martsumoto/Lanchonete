package com.marcelokmats.lanchonete.promotionList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Promotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionListPresenterImpl implements PromotionListPresenter, Callback<List<Promotion>> {

    private PromotionListView mView;

    private SparseArray<Ingredient> mIngredients;


    public PromotionListPresenterImpl(PromotionListView view) {
        this.mView = view;
    }

    @Override
    public void fetchPromotions() {
        Call<List<Promotion>> call = ApiUtils.getInterface().getPromotions();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
        if (response != null && response.body() != null) {
            this.mView.setPromotionList(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Promotion>> call, Throwable t) {
        Log.e("Lanchonete", "Error while fetching promotions", t);
    }
}
