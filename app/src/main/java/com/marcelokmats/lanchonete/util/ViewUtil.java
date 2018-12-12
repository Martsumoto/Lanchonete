package com.marcelokmats.lanchonete.util;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewUtil {

    public static void showProgressBar(View content, ProgressBar progressBar, boolean showProgressBar) {
        if (showProgressBar) {
            content.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public static void showEmptyListContent(View content, TextView emptyListMessage, boolean isEmpty) {
        if (isEmpty) {
            emptyListMessage.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
    }
}
