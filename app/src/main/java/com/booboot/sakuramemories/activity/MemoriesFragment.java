package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboot.sakuramemories.R;

public class MemoriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.memories_fragment, container, false);

        Log.e("D", "MEMORIES FRAGMENT");
        return rootView;
    }
}
