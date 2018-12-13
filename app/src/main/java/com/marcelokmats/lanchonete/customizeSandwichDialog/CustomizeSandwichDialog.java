package com.marcelokmats.lanchonete.customizeSandwichDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;
import com.marcelokmats.lanchonete.util.PriceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomizeSandwichDialog extends DialogFragment implements CustomizeSandwichView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.txtTotalPrice)
    TextView mTxtTotalPrice;

    private CustomizeSandwich mCustomizeSandwich;

    private List<Integer> mCustomIngredientIdsList;
    private SparseArray<Ingredient> mAllIngredients;

    private List<Integer> mMenuSandwichIngredients;

    private SparseArray<CustomIngredient> mCustomIngredients;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        this.fillCustomIngredientsList();
        this.displayIngredientsList(this.mAllIngredients);
        this.updateTotal();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = this.getLayoutInflater().inflate(
                R.layout.customize_sandwich_dialog, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void updateTotal() {
        this.mTxtTotalPrice.setText(NumberFormatterUtil.getCurrencyString(PriceUtil.value(mCustomIngredients)));
    }

    @OnClick(R.id.btnConfirm) void btnConfirm() {
        mCustomIngredientIdsList = IngredientUtil.transformCustomIngredientsList(mCustomIngredients);
        this.mCustomizeSandwich.updateCustomizedSandwich(mCustomIngredientIdsList);
        this.dismiss();
    }

    @OnClick(R.id.btnCancel) void btnCancel() {
        this.dismiss();
    }

    private void displayIngredientsList(SparseArray<Ingredient> ingredients) {
        IngredientsListAdapter adapter;

        adapter = new IngredientsListAdapter(mCustomIngredients, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void setCustomIngredientIdsList(List<Integer> customIngredientIdsList) {
        mCustomIngredientIdsList = customIngredientIdsList;
    }

    public void setCustomizeSandwich(CustomizeSandwich customizeSandwich) {
        mCustomizeSandwich = customizeSandwich;
    }

    public void setAllIngredients(SparseArray<Ingredient> allIngredients) {
        mAllIngredients = allIngredients;
    }

    public void setMenuSandwichIngredients(List<Integer> menuSandwichIngredients) {
        mMenuSandwichIngredients = menuSandwichIngredients;
    }

    public void fillCustomIngredientsList() {
        if (mCustomIngredientIdsList == null) {
            this.mCustomIngredients = IngredientUtil.createCustomIngredients(this.mAllIngredients, mMenuSandwichIngredients);
        } else {
            this.mCustomIngredients = IngredientUtil.createCustomIngredients(this.mAllIngredients, mCustomIngredientIdsList);
        }
    }
}
