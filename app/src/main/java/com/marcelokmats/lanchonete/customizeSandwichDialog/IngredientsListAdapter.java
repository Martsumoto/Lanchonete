package com.marcelokmats.lanchonete.customizeSandwichDialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.util.ImageUtil;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.ViewHolder> {

    private Context mContext;

    private SparseArray<CustomIngredient> mIngredientList;

    private CustomizeSandwichView mView;

    public IngredientsListAdapter(SparseArray<CustomIngredient> ingredientList,
                                  CustomizeSandwichView view) {
        this.mIngredientList = ingredientList;
        mView = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.ingredient_list_item, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        CustomIngredient customIngredient = this.mIngredientList.get(this.mIngredientList.keyAt(i));

        if (customIngredient != null) {
            Ingredient ingredient = customIngredient.getIngredient();

            holder.name.setText(ingredient.getName());
            holder.price.setText(NumberFormatterUtil.getCurrencyString(ingredient.getPrice()));
            ImageUtil.setupImage(this.mContext, ingredient.getImageUrl(), holder.imgIngredient);

            holder.txtAmount.setText(customIngredient.getAmount().toString());
            holder.btnPlus.setOnClickListener(new AmountChangeButtonClickListener(
                    customIngredient, 1, holder.txtAmount, mView));
            holder.btnMinus.setOnClickListener(new AmountChangeButtonClickListener(
                    customIngredient, -1, holder.txtAmount, mView));
        }
    }

    @Override
    public int getItemCount() {
        return this.mIngredientList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;

        @BindView(R.id.txtName)
        TextView name;

        @BindView(R.id.imgIngredient)
        ImageView imgIngredient;

        @BindView(R.id.txtPrice)
        TextView price;

        @BindView(R.id.btnMinus)
        Button btnMinus;

        @BindView(R.id.btnPlus)
        Button btnPlus;

        @BindView(R.id.txtAmount)
        TextView txtAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
