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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;
import com.marcelokmats.lanchonete.util.PriceUtil;
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

    @BindView(R.id.layoutTotal)
    LinearLayout mLayoutTotal;

    @BindView(R.id.txtTotalPrice)
    TextView mTxtTotalPrice;

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
    public void onDestroyView() {
        super.onDestroyView();

        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.fetchOrders();
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

        this.setupTotal(orderList, sandwichList, ingredientList);
    }

    private void showOrderList(List<Order> orderList,
                               SparseArray<Sandwich> sandwichList,
                               SparseArray<Ingredient> ingredientList) {
        OrderListAdapter adapter;

        if (orderList != null && orderList.size() > 0) {
            adapter = new OrderListAdapter(this, orderList, sandwichList, ingredientList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                    this.mTxtEmptyListMessage, ViewUtil.Type.CONTENT);
        } else {
            ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                    this.mTxtEmptyListMessage, ViewUtil.Type.ERROR);
        }
    }

    private void setupTotal(List<Order> orderList,
                            SparseArray<Sandwich> sandwichList,
                            SparseArray<Ingredient> ingredientList) {
        this.mLayoutTotal.setVisibility(View.VISIBLE);
        this.mTxtTotalPrice.setText(NumberFormatterUtil.getCurrencyString(
                PriceUtil.value(orderList, sandwichList, ingredientList)));
    }
}
