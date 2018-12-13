package com.marcelokmats.lanchonete.util;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewUtil {

    public enum Type {
        CONTENT,
        PROGRESSBAR,
        ERROR
    }

    public static void toggleVisibility(View content, View progressBar, View errorMessage, ViewUtil.Type type) {
        switch (type) {
            case CONTENT:
                content.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                errorMessage.setVisibility(View.GONE);
                break;
            case PROGRESSBAR:
                content.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.GONE);
                break;
            case ERROR:
                content.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
                break;
        }
    }
}
