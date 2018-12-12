package com.marcelokmats.lanchonete.promotionList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Promotion;
import com.marcelokmats.lanchonete.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionListFragment extends Fragment implements PromotionListView {

    @BindView(R.id.txtFragmentTitle)
    TextView mTxtFragmentTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.txtEmptyListMessage)
    TextView mTxtEmptyListMessage;

    private PromotionListPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this,view);

        mTxtFragmentTitle.setText(R.string.promotions);
        this.mTxtEmptyListMessage.setText(R.string.promotion_empty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.fetchPromotions();
    }

    @Override
    public void setPresenter(PromotionListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setPromotionList(List<Promotion> promotionList) {
        this.showPromotionList(promotionList);
    }

    private void showPromotionList(List<Promotion> promotionList) {
        PromotionListAdapter adapter;
        RecyclerView list;

        ViewUtil.showProgressBar(this.mRecyclerView, this.mProgressBar, false);

        if (promotionList != null && promotionList.size() > 0) {
            adapter = new PromotionListAdapter(this, promotionList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, false);
        } else {
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, true);
        }
    }
}
