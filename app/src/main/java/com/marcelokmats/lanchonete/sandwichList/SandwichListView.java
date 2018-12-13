package com.marcelokmats.lanchonete.sandwichList;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface SandwichListView {

    void setPresenter(SandwichListPresenter presenter);

    void setSandwichList(List<Sandwich> sandwichList, SparseArray<Ingredient> ingredientList);

    void onSandwichListItemClick(Sandwich sandwich);

    void showProgressBar();

    void hideProgressBar();

    void showTimeoutError();
}
