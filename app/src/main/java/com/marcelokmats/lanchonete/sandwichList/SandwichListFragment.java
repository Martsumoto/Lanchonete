package com.marcelokmats.lanchonete.sandwichList;

import android.content.Intent;
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
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.sandwichDetail.SandwichDetailsActivity;
import com.marcelokmats.lanchonete.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichListFragment extends Fragment implements SandwichListView {

    @BindView(R.id.txtFragmentTitle)
    TextView mTxtFragmentTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.txtEmptyListMessage)
    TextView mTxtEmptyListMessage;

    private SandwichListPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this,view);

        this.mTxtFragmentTitle.setText(R.string.sandwiches_title);
        this.mTxtEmptyListMessage.setText(R.string.sandwich_empty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.fetchSandwiches();
    }

    @Override
    public void setPresenter(SandwichListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setSandwichList(List<Sandwich> sandwichList) {
        this.showSandwichList(sandwichList);
    }

    @Override
    public void onSandwichListItemClick(Sandwich sandwich) {
        Intent intent;

        intent = new Intent(this.getActivity(), SandwichDetailsActivity.class);
        intent.putExtra(SandwichListPresenterImpl.SANDWICH, sandwich);

        this.startActivity(intent);
    }

    private void showSandwichList(List<Sandwich> sandwichList) {
        SandwichListAdapter adapter;

        ViewUtil.showProgressBar(this.mRecyclerView, this.mProgressBar, false);

        if (sandwichList != null && sandwichList.size() > 0) {
            adapter = new SandwichListAdapter(this, sandwichList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, false);
        } else {
            ViewUtil.showEmptyListContent(this.mRecyclerView, this.mTxtEmptyListMessage, true);
        }
    }
}
