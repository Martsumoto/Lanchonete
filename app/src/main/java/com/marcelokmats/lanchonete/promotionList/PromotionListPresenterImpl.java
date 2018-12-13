package com.marcelokmats.lanchonete.promotionList;

import android.util.Log;
import android.util.SparseArray;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Promotion;
import com.marcelokmats.lanchonete.util.RxUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PromotionListPresenterImpl implements PromotionListPresenter {

    private PromotionListView mView;

    private SparseArray<Ingredient> mIngredients;

    private Disposable mPromotionDisposable;

    public PromotionListPresenterImpl(PromotionListView view) {
        this.mView = view;
    }

    @Override
    public void fetchPromotions() {
        this.mView.showProgressBar();
        mPromotionDisposable = ApiUtils.getInterface().getPromotions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPromotionsLoaded, this::onPromtionError);
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(this.mPromotionDisposable);
    }

    private void onPromotionsLoaded(List<Promotion> promotionList) {
        if (promotionList != null) {
            this.mView.setPromotionList(promotionList);
        }

        this.mView.hideProgressBar();
    }

    private void onPromtionError(Throwable t) {
        Log.e("Lanchonete", "Error while fetching promotions", t);
        this.mView.hideProgressBar();
        this.mView.showTimeoutError();
    }

}
