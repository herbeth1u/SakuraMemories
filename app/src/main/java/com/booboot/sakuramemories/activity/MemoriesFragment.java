package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.adapter.GardenAdapter;
import com.booboot.sakuramemories.model.Flower;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class MemoriesFragment extends Fragment {
    private RecyclerView garden;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.memories_fragment, container, false);

        ImageView background = (ImageView) rootView.findViewById(R.id.background);
        Picasso.with(getActivity()).load(R.drawable.grass).centerCrop().fit().into(background);

        garden = (RecyclerView) rootView.findViewById(R.id.garden);
        garden.setLayoutManager(new FlexboxLayoutManager());
        GardenAdapter gardenAdapter = new GardenAdapter(getActivity());
        gardenAdapter.setFlowers(Arrays.asList(new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower(), new Flower()));
        garden.setAdapter(gardenAdapter);

        return rootView;
    }
}
