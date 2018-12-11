package com.marcelokmats.lanchonete.snackList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcelokmats.lanchonete.R;

import butterknife.ButterKnife;

public class SnackListFragment extends Fragment implements SnackListView {

    private SnackListPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.snack_list_fragment, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.fetchIngredients();
    }

    @Override
    public void setPresenter(SnackListPresenter presenter) {
        this.mPresenter = presenter;
    }
}
