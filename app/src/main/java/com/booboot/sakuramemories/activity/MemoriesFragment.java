package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.booboot.sakuramemories.R;
import com.squareup.picasso.Picasso;

public class MemoriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.memories_fragment, container, false);

        ImageView background = (ImageView) rootView.findViewById(R.id.background);
        Picasso.with(getActivity()).load(R.drawable.grass).centerCrop().fit().into(background);
        Log.e("D", "MEMORIES FRAGMENT");
        return rootView;
    }
}
