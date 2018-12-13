package com.marcelokmats.lanchonete.sandwichList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.api.Repository;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.PriceUtil;
import com.marcelokmats.lanchonete.util.RxUtils;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SandwichListPresenterImpl implements SandwichListPresenter {

    private SandwichListView mView;

    private SparseArray<Ingredient> mIngredients;

    public static final String SANDWICH = "SANDWICH";

    private Disposable mSandwichDisposable;

    public SandwichListPresenterImpl(SandwichListView view) {
        this.mView = view;
    }

    @Override
    public void fetchSandwiches() {
        this.mView.showProgressBar();
        mSandwichDisposable = ApiUtils.getInterface().getSandwiches().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSandwichLoaded, this::onSandwichError);
    }

    @Override
    public BigDecimal calculatePrice(List<Ingredient> ingredients) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        totalPrice = PriceUtil.value(ingredients);

        return totalPrice;
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(this.mSandwichDisposable);
    }

    private void onSandwichLoaded(List<Sandwich> sandwichList) {
        if (sandwichList != null) {
            this.mView.setSandwichList(sandwichList, Repository.getInstance().getAllIngredients());
        }
        this.mView.hideProgressBar();
    }

    private void onSandwichError(Throwable t) {
        Log.e("Lanchonete", "Error while fetching sandwiches", t);
        this.mView.showTimeoutError();
    }
}
