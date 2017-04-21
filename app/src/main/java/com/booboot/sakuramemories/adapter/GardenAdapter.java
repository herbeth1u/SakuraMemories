package com.booboot.sakuramemories.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.image.BlurTransform;
import com.booboot.sakuramemories.image.CircleTransform;
import com.booboot.sakuramemories.image.ClickEffect;
import com.booboot.sakuramemories.model.Flower;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GardenAdapter extends RecyclerView.Adapter<FlowerHolder> implements Filterable, View.OnClickListener {
    private Activity activity;
    private List<Flower> flowers;
    private List<Flower> filteredFlowers;
    private ItemFilter mFilter = new ItemFilter();

    public GardenAdapter(Activity activity) {
        this.activity = activity;
        this.flowers = new ArrayList<>();
        this.filteredFlowers = flowers;
    }

    @Override
    public FlowerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_item, parent, false);
        return new FlowerHolder(v);
    }

    @Override
    public void onBindViewHolder(FlowerHolder holder, int position) {
        Flower flower = filteredFlowers.get(position);

        Picasso.with(activity).load(R.drawable.flower_1_icon).transform(new CircleTransform()).fit().into(holder.getFlowerImage());

        ClickEffect.addClickEffect(holder.getFlowerImage());
        holder.getFlowerImage().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final AppCompatDialog dialog = new AppCompatDialog(activity);
        dialog.setContentView(R.layout.flower_dialog);

        activity.findViewById(R.id.screenLoading).setVisibility(View.VISIBLE);
        ImageView blurBackground = (ImageView) dialog.findViewById(R.id.blurBackground);
        Picasso.with(activity).load(R.drawable.flower_1_bg).transform(new BlurTransform(activity)).into(blurBackground, new Callback() {
            @Override
            public void onSuccess() {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                if (dialog.getWindow() != null) {
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                } else {
                    dialog.show();
                }
                activity.findViewById(R.id.screenLoading).setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                activity.findViewById(R.id.screenLoading).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredFlowers.size();
    }

    public void clearAll() {
        flowers.clear();
        notifyDataSetChanged();
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
        this.filteredFlowers = flowers;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().trim().toLowerCase();
            FilterResults results = new FilterResults();

            int count = flowers.size();
            final ArrayList<Flower> nlist = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                String filterableString = flowers.get(i).getType();
                if (filterableString.trim().toLowerCase().contains(filterString)) {
                    nlist.add(flowers.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredFlowers = (ArrayList<Flower>) results.values;
            notifyDataSetChanged();
        }
    }
}
