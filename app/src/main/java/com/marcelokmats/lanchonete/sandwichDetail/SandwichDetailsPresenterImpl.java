package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.IngredientUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SandwichDetailsPresenterImpl implements SandwichDetailsPresenter,
        Callback<List<Ingredient>> {

    private SandwichDetailsView mView;

    private Sandwich mSandwich;

    private SparseArray<Ingredient> mAllIngredients;

    private List<Integer> mCustomIngredients = null;

    public SandwichDetailsPresenterImpl(SandwichDetailsView mView) {
        this.mView = mView;
    }

    @Override
    public void fetchSandwichIngredients() {
        if (this.mSandwich != null) {
            Call<List<Ingredient>> call = ApiUtils.getInterface().getIngredients();
            call.enqueue(this);
        }
    }

    @Override
    public void setSandwich(Sandwich sandwich) {
        this.mSandwich = sandwich;
    }

    @Override
    public void insertSandwich() {
        Call<Void> call = null;
        JSONArray jsonArray = null;

        if (mCustomIngredients != null) {
            jsonArray = new JSONArray(this.mCustomIngredients);

            call = ApiUtils.getInterface().insertCustomSandwichIntoOrder(
                    this.mSandwich.getId(), jsonArray.toString());
        } else {
            call = ApiUtils.getInterface().insertSandwichIntoOrder(this.mSandwich.getId());
        }

        call.enqueue(new OrderInsertionCallback(this));
    }

    @Override
    public void insertionSuccessful() {
        this.mView.finishActivity();
    }

    @Override
    public SparseArray<Ingredient> getAllIngredients() {
        return this.mAllIngredients;
    }

    @Override
    public List<Integer> getCustomIngredients() {
        return this.mCustomIngredients;
    }

    @Override
    public void updateCustomizedSandwich(List<Integer> customIngredientsId) {
        if (mCustomIngredients == null) {
            mCustomIngredients = new ArrayList<>();
        }
        this.mCustomIngredients.clear();
        this.mCustomIngredients.addAll(customIngredientsId);
        this.mView.populateSandwichInfo(this.mSandwich, this.mCustomIngredients, this.mAllIngredients);
    }

    @Override
    public Sandwich getSandwich() {
        return this.mSandwich;
    }

    @Override
    public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
        if (response != null && response.body() != null) {
            this.mAllIngredients = IngredientUtil.transformListIntoSparseArray(response.body());
            this.mView.populateSandwichInfo(this.mSandwich, this.mCustomIngredients, this.mAllIngredients);
            this.mView.setActionBarTitle(mSandwich.getName());
            this.mView.showProgressBar(false);
            Log.d("Lanchonete", "Sandwich ingredients received");
        } else {
            Log.d("Lanchonete", "Could not load sandwich ingredients");
        }
    }

    @Override
    public void onFailure(Call<List<Ingredient>> call, Throwable t) {
        Log.e("Lanchonete", "Could not load sandwich ingredients", t);
    }
}
