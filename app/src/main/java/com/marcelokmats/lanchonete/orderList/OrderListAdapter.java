package com.marcelokmats.lanchonete.orderList;

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
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.ImageUtil;
import com.marcelokmats.lanchonete.util.IngredientUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private List<Order> mOrderList;

    private Context mContext;

    private OrderListView mView;

    private SparseArray<Sandwich> mSandwichList;

    private SparseArray<Ingredient> mIngredientList;

    public OrderListAdapter(OrderListView view, List<Order> orderList,
                            SparseArray<Sandwich> sandwichList,
                            SparseArray<Ingredient> ingredientList) {
        this.mOrderList = orderList;
        this.mSandwichList = sandwichList;
        this.mIngredientList = ingredientList;
        this.mView = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.order_list_item, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Order order = mOrderList.get(i);
        Sandwich sandwich = null;
        List<Integer> sandwichIngredients;

        if (order != null) {
            holder.order = order;
            sandwich = this.mSandwichList.get(order.getSandwichId());

            if (order.getIngredients() != null) {
                // Has custom ingredients
                sandwichIngredients = order.getIngredients();
            } else {
                // No custom ingredients, use default menu sandwich ingredients
                sandwichIngredients = sandwich.getIngredients();
            }

            holder.ingredients.setText(IngredientUtil.getIngredientsAsString(sandwichIngredients, this.mIngredientList));


            if (sandwich != null) {
                holder.name.setText(sandwich.getName());
                ImageUtil.setupImage(this.mContext, sandwich.getImageUrl(), holder.imgSandwich);
            }

        }
    }

    @Override
    public int getItemCount() {
        return this.mOrderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Order order;
        View mItemView;

        @BindView(R.id.txtName)
        TextView name;

        @BindView(R.id.imgSandwich)
        ImageView imgSandwich;

        @BindView(R.id.txtIngredients)
        TextView ingredients;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
