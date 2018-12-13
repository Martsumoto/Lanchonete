package com.marcelokmats.lanchonete.sandwichList;

import android.content.Intent;
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
    public void onDestroyView() {
        super.onDestroyView();

        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
    }

    @Override
    public void setPresenter(SandwichListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setSandwichList(List<Sandwich> sandwichList,
                                SparseArray<Ingredient> ingredientList) {
        this.showSandwichList(sandwichList, ingredientList);
    }

    @Override
    public void onSandwichListItemClick(Sandwich sandwich) {
        Intent intent;

        intent = new Intent(this.getActivity(), SandwichDetailsActivity.class);
        intent.putExtra(SandwichListPresenterImpl.SANDWICH, sandwich);

        this.startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.PROGRESSBAR);
    }

    @Override
    public void hideProgressBar() {
        ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.CONTENT);
    }

    @Override
    public void showTimeoutError() {
        ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.ERROR);
    }

    private void showSandwichList(List<Sandwich> sandwichList,
                                  SparseArray<Ingredient> ingredientList) {
        SandwichListAdapter adapter;


        if (sandwichList != null && sandwichList.size() > 0) {
            adapter = new SandwichListAdapter(this, sandwichList, ingredientList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                    this.mTxtEmptyListMessage, ViewUtil.Type.CONTENT);
        } else {
            ViewUtil.toggleVisibility(this.mRecyclerView, this.mProgressBar,
                    this.mTxtEmptyListMessage, ViewUtil.Type.ERROR);
        }
    }
}
