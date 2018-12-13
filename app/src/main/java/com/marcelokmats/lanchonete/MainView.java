package com.marcelokmats.lanchonete;

public interface MainView {

    void onValuesFetched();

    void showTimeoutError();

    void showProgressBar();

    void hideProgressBar();
}
