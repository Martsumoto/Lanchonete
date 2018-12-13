package com.marcelokmats.lanchonete.promotionList;

import com.marcelokmats.lanchonete.model.Promotion;

import java.util.List;

public interface PromotionListView {

    void setPresenter(PromotionListPresenter presenter);

    void setPromotionList(List<Promotion> promotionList);

    void showTimeoutError();

    void showProgressBar();

    void hideProgressBar();
}
