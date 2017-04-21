package com.booboot.sakuramemories.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.image.CircleTransform;
import com.booboot.sakuramemories.image.ClickEffect;
import com.booboot.sakuramemories.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GardenAdapter extends RecyclerView.Adapter<FlowerHolder> implements Filterable {
    private Context context;
    private List<Flower> flowers;
    private List<Flower> filteredFlowers;
    private ItemFilter mFilter = new ItemFilter();

    public GardenAdapter(Context context) {
        this.context = context;
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

        Picasso.with(context).load(R.drawable.flower_1_icon).transform(new CircleTransform()).fit().into(holder.getFlowerImage());

        ClickEffect.addClickEffect(holder.getFlowerImage());
        holder.getFlowerImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("D", "clicked ?");
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
