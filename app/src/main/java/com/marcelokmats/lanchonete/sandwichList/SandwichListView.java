package com.marcelokmats.lanchonete.sandwichList;

import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public interface SandwichListView {

    void setPresenter(SandwichListPresenter presenter);

    void setSandwichList(List<Sandwich> sandwichList);

    void onSandwichListItemClick(Sandwich sandwich);
}
