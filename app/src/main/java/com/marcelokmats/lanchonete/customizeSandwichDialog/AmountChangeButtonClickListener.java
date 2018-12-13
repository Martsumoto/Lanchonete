package com.marcelokmats.lanchonete.customizeSandwichDialog;

import android.view.View;
import android.widget.TextView;

import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.util.IngredientUtil;

public class AmountChangeButtonClickListener implements View.OnClickListener {

    private CustomIngredient mCustomIngredient;

    private int mAmountChange = 0;

    private TextView mAmountTextView;

    private CustomizeSandwichView mView;

    public AmountChangeButtonClickListener(CustomIngredient customIngredient,
                                           int amountChange,
                                           TextView amountTextView,
                                           CustomizeSandwichView view) {
        mCustomIngredient = customIngredient;
        mAmountChange = amountChange;
        mAmountTextView = amountTextView;
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
        this.mAmountTextView.setText(this.mCustomIngredient.getAmount().toString());
        this.mView.updateTotal();
    }
}
