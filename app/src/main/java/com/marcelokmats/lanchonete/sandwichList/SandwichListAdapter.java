package com.marcelokmats.lanchonete.sandwichList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.ImageUtil;
import com.marcelokmats.lanchonete.util.IngredientUtil;
import com.marcelokmats.lanchonete.util.NumberFormatterUtil;
import com.marcelokmats.lanchonete.util.PriceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.ViewHolder> {

    private List<Sandwich> mSandwichList;

    private Context mContext;

    private SandwichListView mView;

    private SparseArray<Ingredient> mAllIngredients;

    public SandwichListAdapter(SandwichListView view, List<Sandwich> sandwichList,
                               SparseArray<Ingredient> ingredientList) {
        this.mSandwichList = sandwichList;
        this.mView = view;
        this.mAllIngredients = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.sandwich_list_item, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Sandwich sandwich = mSandwichList.get(i);

        if (sandwich != null) {
            holder.sandwich = sandwich;

            holder.name.setText(sandwich.getName());

            ImageUtil.setupImage(this.mContext, sandwich.getImageUrl(), holder.imgSandwich);
            holder.ingredients.setText(IngredientUtil.getIngredientsAsString(
                    sandwich.getIngredients(), mAllIngredients));
            holder.price.setText(NumberFormatterUtil.getCurrencyString(
                    PriceUtil.value(sandwich.getIngredients(), this.mAllIngredients)));
        }
    }

    @Override
    public int getItemCount() {
        return this.mSandwichList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        Sandwich sandwich;
        View mItemView;

        @BindView(R.id.txtName)
        TextView name;

        @BindView(R.id.txtIngredients)
        TextView ingredients;

        @BindView(R.id.txtPrice)
        TextView price;

        @BindView(R.id.imgSandwich)
        ImageView imgSandwich;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.root)
        public void onChatButtonClick() {
            mView.onSandwichListItemClick(this.sandwich);
        }
    }
}
