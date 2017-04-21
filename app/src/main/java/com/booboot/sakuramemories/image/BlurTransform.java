package com.booboot.sakuramemories.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class BlurTransform implements Transformation {
    private Context context;

    public BlurTransform(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        Bitmap res = BitmapTransformation.darkBlur(context, bitmap);
        bitmap.recycle();
        return res;
    }

    @Override
    public String key() {
        return "darkblur";
    }
}