package com.marcelokmats.lanchonete.promotionList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Promotion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionListAdapter extends RecyclerView.Adapter<PromotionListAdapter.ViewHolder> {

    private List<Promotion> mPromotionList;

    private Context mContext;

    private PromotionListView mView;


    public PromotionListAdapter(PromotionListView view, List<Promotion> promotionList) {
        this.mPromotionList = promotionList;
        this.mView = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.promotion_list_item, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Promotion promotion = mPromotionList.get(i);

        if (promotion != null) {
            holder.promotion = promotion;

            holder.name.setText(promotion.getName());
            holder.description.setText(promotion.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return this.mPromotionList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        Promotion promotion;
        View mItemView;

        @BindView(R.id.txtName)
        TextView name;

        @BindView(R.id.txtDescription)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }

    }
}
