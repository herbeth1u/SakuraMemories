package com.booboot.sakuramemories.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.booboot.sakuramemories.R;

public class FlowerHolder extends RecyclerView.ViewHolder {
    private ImageView flowerImage;

    public FlowerHolder(View itemView) {
        super(itemView);
        flowerImage = (ImageView) itemView.findViewById(R.id.flowerImage);
    }

    public ImageView getFlowerImage() {
        return flowerImage;
    }

    public void setFlowerImage(ImageView flowerImage) {
        this.flowerImage = flowerImage;
    }
}