package com.marcelokmats.lanchonete.sandwichDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.customizeSandwichDialog.CustomizeSandwich;
import com.marcelokmats.lanchonete.customizeSandwichDialog.CustomizeSandwichDialog;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.sandwichList.SandwichListPresenterImpl;
import com.marcelokmats.lanchonete.util.ImageUtil;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;
import com.marcelokmats.lanchonete.util.PriceUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichDetailsActivity extends AppCompatActivity implements SandwichDetailsView, CustomizeSandwich, View.OnClickListener {

    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.layoutContent) ConstraintLayout mLayoutContent;
    @BindView(R.id.imgSandwich) ImageView mImgSandwich;
    @BindView(R.id.txtPrice) TextView mTxtPrice;
    @BindView(R.id.txtIngredients) TextView mTxtIngredients;
    @BindView(R.id.btnAddCart) Button mBtnAddCart;
    @BindView(R.id.btnCustomize) TextView mBtnCustomize;

    @Inject
    SandwichDetailsPresenter mPresenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sandwich_details_activity);
        ButterKnife.bind(this);

        SandwichDetailsComponent component = DaggerSandwichDetailsComponent.builder().sandwichDetailsModule(
                new SandwichDetailsModule(this)).build();
        component.injectSandwichDetailsPresenter(this);

        this.mBtnAddCart.setOnClickListener(this);
        this.mBtnCustomize.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.showProgressBar(true);
        this.setActionBarTitle(null);
        this.loadIntent();
        this.mPresenter.fetchSandwichIngredients();
    }

    @Override
    public void setActionBarTitle(String title) {
        ActionBar actionbar = this.getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);

            if (title != null && !title.isEmpty()) {
                actionbar.setTitle(title);
            } else {
                actionbar.setTitle(R.string.details_title);
            }
        }
    }

    @Override
    public void showProgressBar(boolean showProgressBar) {
        if (showProgressBar) {
            this.mProgressBar.setVisibility(View.VISIBLE);
            this.mLayoutContent.setVisibility(View.GONE);
        } else {
            this.mProgressBar.setVisibility(View.GONE);
            this.mLayoutContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void populateSandwichInfo(Sandwich sandwich, List<Integer> ingredients,
                                     SparseArray<Ingredient> allIngredientList) {
        List<Integer> sandwichIngredients;
        String sandwichName = sandwich.getName();

        if (ingredients != null) {
            sandwichIngredients = ingredients;
            sandwichName = sandwichName + " " + this.getString(R.string.your_way_suffix);
        } else {
            sandwichIngredients = sandwich.getIngredients();
        }

        ImageUtil.setupImage(this, sandwich.getImageUrl(), this.mImgSandwich);
        this.setActionBarTitle(sandwichName);
        this.mTxtPrice.setText(NumberFormatterUtil.getCurrencyString(PriceUtil.value(sandwichIngredients, allIngredientList)));
        this.mTxtIngredients.setText(IngredientUtil.getIngredientsAsString(sandwichIngredients, allIngredientList));
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateCustomizedSandwich(List<Integer> customIngredientsId) {
        this.mPresenter.updateCustomizedSandwich(customIngredientsId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.mBtnAddCart.getId()) {
            this.mPresenter.insertSandwich();
        } else if (v.getId() == this.mBtnCustomize.getId()) {
            this.showCustomizeSandwichDialog();
        }
    }

    private void loadIntent() {
        Intent intent;
        intent = this.getIntent();

        Sandwich sandwich = intent.getParcelableExtra(SandwichListPresenterImpl.SANDWICH);
        this.mPresenter.setSandwich(sandwich);
    }

    private void showCustomizeSandwichDialog() {
        // Local variables
        CustomizeSandwichDialog dialog = new CustomizeSandwichDialog();

        dialog.setRetainInstance(true);
        dialog.setAllIngredients(this.mPresenter.getAllIngredients());
        dialog.setCustomIngredientIdsList(this.mPresenter.getCustomIngredients());
        dialog.setMenuSandwichIngredients(this.mPresenter.getSandwich().getIngredients());
        dialog.setCustomizeSandwich(this);
        dialog.show(this.getSupportFragmentManager(), "");
    }
}
