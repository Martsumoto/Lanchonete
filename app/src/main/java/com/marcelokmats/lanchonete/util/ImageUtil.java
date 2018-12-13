package com.marcelokmats.lanchonete.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marcelokmats.lanchonete.R;

public class ImageUtil {

    public static void setupImage(Context context, String imageUrl, ImageView imageHolder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_load_error);
        requestOptions.error(R.drawable.image_load_error);

        Glide.with(context).setDefaultRequestOptions(requestOptions).load(imageUrl).into(imageHolder);
    }
}
