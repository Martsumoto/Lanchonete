package com.marcelokmats.lanchonete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.marcelokmats.lanchonete.snackList.SnackListFragment;
import com.marcelokmats.lanchonete.snackList.SnackListPresenter;
import com.marcelokmats.lanchonete.snackList.SnackListPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String SNACKS_FRAGMENT = "SNACKS_FRAGMENT";
    private static final String SELECTED_FRAGMENT = "SELECTED_FRAGMENT";

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;

    private SnackListFragment mSnackListFragment = null;

    private SnackListPresenter mSnackListPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        this.setupInitialFragment(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_FRAGMENT, bottomNavigationView.getSelectedItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.replaceFragment(menuItem.getItemId());
        return true;
    }

    private void setupInitialFragment(Bundle savedInstanceState) {
        int selectedFragment = R.id.action_snacks;

        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            mSnackListFragment = (SnackListFragment) fm.findFragmentByTag(SNACKS_FRAGMENT);

            selectedFragment = savedInstanceState.getInt(SELECTED_FRAGMENT);
        }

        this.replaceFragment(selectedFragment);
    }

    private void replaceFragment(int id) {
        Fragment selectedFragment = null;
        String tag = null;

        switch (id) {
            case R.id.action_snacks:
                selectedFragment = this.getSnackListFragment();
                tag = SNACKS_FRAGMENT;
                break;
            case R.id.action_promotions:
            case R.id.action_order:
                break;
        }

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, selectedFragment, tag);
        transaction.commit();
    }

    private SnackListFragment getSnackListFragment() {
        if (this.mSnackListFragment == null) {
            this.mSnackListFragment = new SnackListFragment();
            this.mSnackListPresenter = new SnackListPresenterImpl(this.mSnackListFragment);
            this.mSnackListFragment.setPresenter(this.mSnackListPresenter);
        }

        return this.mSnackListFragment;
    }
}
