package com.marcelokmats.lanchonete.sandwichList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelokmats.lanchonete.R;
import com.marcelokmats.lanchonete.model.Sandwich;
import com.marcelokmats.lanchonete.util.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.ViewHolder> {

    private List<Sandwich> mSandwichList;

    private Context mContext;

    private SandwichListView mView;


    public SandwichListAdapter(SandwichListView view, List<Sandwich> sandwichList) {
        this.mSandwichList = sandwichList;
        this.mView = view;
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
