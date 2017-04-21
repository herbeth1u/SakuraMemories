package com.booboot.sakuramemories.image;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ClickEffect {
    public static void addClickEffect(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x33000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        return false;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                }
                return false;
            }
        });
    }
}
