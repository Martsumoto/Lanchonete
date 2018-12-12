package com.marcelokmats.lanchonete.customizeSandwichDialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcelokmats.lanchonete.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomizeSandwichDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = this.getLayoutInflater().inflate(
                R.layout.customize_sandwich_dialog, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btnConfirm) void btnConfirm() {
        this.dismiss();
    }

    @OnClick(R.id.btnCancel) void btnCancel() {
        this.dismiss();
    }
}
