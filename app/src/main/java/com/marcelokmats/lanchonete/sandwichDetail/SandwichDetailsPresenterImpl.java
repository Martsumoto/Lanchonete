package com.marcelokmats.lanchonete.sandwichDetail;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.RxUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SandwichDetailsPresenterImpl implements SandwichDetailsPresenter {

    private SandwichDetailsView mView;

    private Sandwich mSandwich;

    private SparseArray<Ingredient> mAllIngredients;

    private List<Integer> mCustomIngredients = null;

    private Disposable mIngredientsDisposable;

    private Disposable mInsertNormalSandwichDisposable;

    public SandwichDetailsPresenterImpl(SandwichDetailsView mView) {
        this.mView = mView;
    }

    @Override
    public void fetchSandwichIngredients() {
        this.mView.showProgressBar();
        if (this.mSandwich != null) {
            mIngredientsDisposable = ApiUtils.getInterface().getIngredients().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onIngredientsLoaded, this::onSandwichIngredientError);
        }
    }

    @Override
    public void setSandwich(Sandwich sandwich) {
        this.mSandwich = sandwich;
    }

    @Override
    public void insertSandwich() {
        JSONArray jsonArray = null;

        if (mCustomIngredients != null) {
            jsonArray = new JSONArray(this.mCustomIngredients);

            mInsertNormalSandwichDisposable = ApiUtils.getInterface()
                    .insertCustomSandwichIntoOrder(this.mSandwich.getId(), jsonArray.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(v -> insertionSuccessful(), this::insertionError);
        } else {
            mInsertNormalSandwichDisposable = ApiUtils.getInterface()
                    .insertSandwichIntoOrder(this.mSandwich.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(v -> insertionSuccessful(), this::insertionError);
        }
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
    public void onDestroy() {
        RxUtils.unsubscribe(this.mIngredientsDisposable, mInsertNormalSandwichDisposable);
    }

    @Override
    public Sandwich getSandwich() {
        return this.mSandwich;
    }

    private void onIngredientsLoaded(List<Ingredient> ingredientList) {
        if (ingredientList != null) {
            this.mAllIngredients = IngredientUtil.transformListIntoSparseArray(ingredientList);
            this.mView.populateSandwichInfo(this.mSandwich, this.mCustomIngredients, this.mAllIngredients);
            this.mView.setActionBarTitle(mSandwich.getName());
            this.mView.hideProgressBar();
            Log.d("Lanchonete", "Sandwich ingredients received");
        } else{
            this.mView.showTimeoutError();
            Log.d("Lanchonete", "Could not load sandwich ingredients");
        }
    }

    private void onSandwichIngredientError(Throwable t) {
        Log.e("Lanchonete", "Could not load sandwich ingredients", t);
        this.mView.showTimeoutError();
    }

    private void insertionSuccessful() {
        this.mView.finishActivity();
    }

    private void insertionError(Throwable t) {
        Log.e("Lanchonete", "Error while inserting a sandwich", t);
    }
}
