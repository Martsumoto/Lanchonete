package com.marcelokmats.lanchonete;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.api.Repository;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.RxUtils;
import com.marcelokmats.lanchonete.util.SandwichUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    private SparseArray<Ingredient> mAllIngredients;

    private SparseArray<Sandwich> mAllSandwiches;

    private MainView mView;

    private Disposable mIngredientsDisposable;

    private Disposable mSandwichesDisposable;

    public MainPresenterImpl(MainView view) {
        mView = view;
    }


    @Override
    public void prefetchData() {
        this.mView.showProgressBar();
        if (Repository.getInstance().getAllIngredients() != null &&
                Repository.getInstance().getAllSandwiches() != null) {
            this.onValuesFetched();
        } else {
            mIngredientsDisposable = ApiUtils.getInterface().getIngredients().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::ingredientsFetched, this::onIngredientsError);
            mSandwichesDisposable = ApiUtils.getInterface().getSandwiches().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::sandwichesFetched, this::onSandwichesError);
        }
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(mIngredientsDisposable, mSandwichesDisposable);
    }

    @Override
    public void ingredientsFetched(List<Ingredient> ingredientList) {
        mAllIngredients = IngredientUtil.transformListIntoSparseArray(ingredientList);
        Repository.getInstance().setAllIngredients(mAllIngredients);
        if (this.mAllSandwiches != null) {
            this.onValuesFetched();
        }
    }

    @Override
    public void sandwichesFetched(List<Sandwich> sandwichList) {
        mAllSandwiches = SandwichUtil.transformListIntoSparseArray(sandwichList);
        Repository.getInstance().setAllSandwiches(mAllSandwiches);
        if (this.mAllIngredients != null) {
            this.onValuesFetched();
        }
    }

    private void onValuesFetched() {
        this.mView.hideProgressBar();
        mView.onValuesFetched();
    }

    private void onIngredientsError(Throwable t) {
        Log.e("Lanchonete", "Could not fetch all ingredients", t);
        mView.showTimeoutError();
    }

    private void onSandwichesError(Throwable t) {
        Log.e("Lanchonete", "Could not fetch all sandwiches", t);
        mView.showTimeoutError();
    }
}
