package com.marcelokmats.lanchonete.orderList;

import android.util.Log;

import com.marcelokmats.lanchonete.api.ApiUtils;
import com.marcelokmats.lanchonete.api.Repository;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.util.RxUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderListPresenterImpl implements OrderListPresenter {

    private OrderListView mView;

    private Disposable mOrderDisposable;

    public OrderListPresenterImpl(OrderListView view) {
        this.mView = view;
    }

    @Override
    public void fetchOrders() {
        this.mOrderDisposable = ApiUtils.getInterface().getOrders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onOrdersLoaded, this::onError);
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(mOrderDisposable);
    }


    private void onOrdersLoaded(List<Order> orderList) {
        if (orderList != null) {
            this.mView.setOrderList(orderList, Repository.getInstance().getAllSandwiches(),
                    Repository.getInstance().getAllIngredients());
        }
    }

    private void onError(Throwable t) {
        Log.e("Lanchonete", "Error while fetching order", t);
    }

}
