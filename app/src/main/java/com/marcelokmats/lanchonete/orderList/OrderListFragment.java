package com.marcelokmats.lanchonete.orderList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListFragment extends Fragment implements OrderListView {

    @BindView(R.id.txtFragmentTitle)
    TextView mTxtFragmentTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.txtEmptyListMessage)
    TextView mTxtEmptyListMessage;

    private OrderListPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this,view);

        this.mTxtFragmentTitle.setText(R.string.orders_title);
        this.mTxtEmptyListMessage.setText(R.string.order_empty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.fetchAllIngredients();
        this.mPresenter.fetchAllSandwiches();
    }

    @Override
    public void setPresenter(OrderListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setOrderList(List<Order> orderList,
                             SparseArray<Sandwich> sandwichList,
                             SparseArray<Ingredient> ingredientList) {
        this.showOrderList(orderList, sandwichList, ingredientList);
    }

    private void showOrderList(List<Order> orderList,
                               SparseArray<Sandwich> sandwichList,
                               SparseArray<Ingredient> ingredientList) {
        OrderListAdapter adapter;

        ViewUtil.showProgressBar(this.mRecyclerView, this.mProgressBar, false);

        if (orderList != null && orderList.size() > 0) {
            adapter = new OrderListAdapter(this, orderList, sandwichList, ingredientList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, false);
        } else {
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, true);
        }
    }
}
