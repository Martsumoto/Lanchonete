package com.marcelokmats.lanchonete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelokmats.lanchonete.orderList.OrderListFragment;
import com.marcelokmats.lanchonete.orderList.OrderListPresenter;
import com.marcelokmats.lanchonete.orderList.OrderListPresenterImpl;
import com.marcelokmats.lanchonete.promotionList.PromotionListFragment;
import com.marcelokmats.lanchonete.promotionList.PromotionListPresenter;
import com.marcelokmats.lanchonete.promotionList.PromotionListPresenterImpl;
import com.marcelokmats.lanchonete.sandwichList.SandwichListFragment;
import com.marcelokmats.lanchonete.sandwichList.SandwichListPresenter;
import com.marcelokmats.lanchonete.sandwichList.SandwichListPresenterImpl;
import com.marcelokmats.lanchonete.util.ViewUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String SANDWICHES_FRAGMENT = "SANDWICHES_FRAGMENT";
    private static final String PROMOTIONS_FRAGMENT = "PROMOTIONS_FRAGMENT";
    private static final String ORDERS_FRAGMENT = "ORDERS_FRAGMENT";
    private static final String SELECTED_FRAGMENT = "SELECTED_FRAGMENT";

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.txtEmptyListMessage)
    TextView mTxtEmptyListMessage;

    private SandwichListFragment mSandwichListFragment = null;
    private SandwichListPresenter mSandwichListPresenter;

    private PromotionListFragment mPromotionListFragment = null;
    private PromotionListPresenter mPromotionListPresenter;

    private OrderListFragment mOrderListFragment = null;
    private OrderListPresenter mOrderListPresenter;

    private int mSelectedFragment = R.id.action_sandwich;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        MainComponent component = DaggerMainComponent.builder().mainModule(
                new MainModule(this)).build();
        component.injectMainPresenter(this);
        this.setupInitialFragment(savedInstanceState);

        mPresenter.prefetchData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_FRAGMENT, mBottomNavigationView.getSelectedItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.replaceFragment(menuItem.getItemId());
        return true;
    }

    @Override
    public void onValuesFetched() {
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.replaceFragment(mSelectedFragment);
    }

    @Override
    public void showTimeoutError() {
        this.mTxtEmptyListMessage.setText(R.string.server_error);
        ViewUtil.toggleVisibility(this.mFrameLayout, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.ERROR);
    }

    @Override
    public void showProgressBar() {
        ViewUtil.toggleVisibility(this.mFrameLayout, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.PROGRESSBAR);
    }

    @Override
    public void hideProgressBar() {
        ViewUtil.toggleVisibility(this.mFrameLayout, this.mProgressBar,
                this.mTxtEmptyListMessage, ViewUtil.Type.CONTENT);
    }

    private void setupInitialFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            mSandwichListFragment = (SandwichListFragment) fm.findFragmentByTag(SANDWICHES_FRAGMENT);

            mSelectedFragment = savedInstanceState.getInt(SELECTED_FRAGMENT);
        }
    }

    private void replaceFragment(int id) {
        Fragment selectedFragment = null;
        String tag = null;

        switch (id) {
            case R.id.action_sandwich:
                selectedFragment = this.getSandwichListFragment();
                tag = SANDWICHES_FRAGMENT;
                break;
            case R.id.action_promotion:
                selectedFragment = this.getPromotionListFragment();
                tag = PROMOTIONS_FRAGMENT;
                break;
            case R.id.action_order:
                selectedFragment = this.getOrderListFragment();
                tag = ORDERS_FRAGMENT;
                break;
        }

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, selectedFragment, tag);
        transaction.commit();
    }

    private SandwichListFragment getSandwichListFragment() {
        if (this.mSandwichListFragment == null) {
            this.mSandwichListFragment = new SandwichListFragment();
            this.mSandwichListPresenter = new SandwichListPresenterImpl(this.mSandwichListFragment);
            this.mSandwichListFragment.setPresenter(this.mSandwichListPresenter);
        }

        return this.mSandwichListFragment;
    }

    private PromotionListFragment getPromotionListFragment() {
        if (this.mPromotionListFragment == null) {
            this.mPromotionListFragment = new PromotionListFragment();
            this.mPromotionListPresenter = new PromotionListPresenterImpl(this.mPromotionListFragment);
            this.mPromotionListFragment.setPresenter(this.mPromotionListPresenter);
        }

        return this.mPromotionListFragment;
    }

    private OrderListFragment getOrderListFragment() {
        if (this.mOrderListFragment == null) {
            this.mOrderListFragment = new OrderListFragment();
            this.mOrderListPresenter = new OrderListPresenterImpl(this.mOrderListFragment);
            this.mOrderListFragment.setPresenter(this.mOrderListPresenter);
        }

        return this.mOrderListFragment;
    }
}
