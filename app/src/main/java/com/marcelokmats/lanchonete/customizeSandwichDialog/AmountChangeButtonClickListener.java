package com.marcelokmats.lanchonete.customizeSandwichDialog;

import android.view.View;

import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;

import java.math.BigDecimal;

public class AmountChangeButtonClickListener implements View.OnClickListener {

    private CustomIngredient mCustomIngredient;

    private int mAmountChange = 0;

    private IngredientsListAdapter.ViewHolder mViewHolder;

    private CustomizeSandwichView mView;

    public AmountChangeButtonClickListener(CustomIngredient customIngredient,
                                           int amountChange,
                                           IngredientsListAdapter.ViewHolder viewHolder,
                                           CustomizeSandwichView view) {
        mCustomIngredient = customIngredient;
        mAmountChange = amountChange;
        mViewHolder = viewHolder;
        mView = view;
    }

    @Override
    public void onClick(View v) {
        // Does not allow amount change to below zero or above the maximum allowed
        if (mAmountChange < 0 && mCustomIngredient.getAmount() == 0) {
            mAmountChange = 0;
        } else if (mAmountChange > 0 &&
                mCustomIngredient.getAmount() >= IngredientUtil.MAXIMUM_INGREDIENT_AMOUNT ) {
            mAmountChange = 0;
        }

        this.mCustomIngredient.setAmount(this.mCustomIngredient.getAmount() + mAmountChange);
        this.mViewHolder.txtAmount.setText(this.mCustomIngredient.getAmount().toString());
        this.mViewHolder.totalPrice.setText(NumberFormatterUtil.getCurrencyString(
                mCustomIngredient.getIngredient().getPrice().multiply(
                        BigDecimal.valueOf(mCustomIngredient.getAmount()))));
        this.mView.updateTotal();
    }
}
