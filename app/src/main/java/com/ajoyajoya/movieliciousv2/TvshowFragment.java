package com.ajoyajoya.movieliciousv2;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private TvAdapter adapter;
    private ProgressBar progressBar;

    // --Commented out by Inspection (2019-07-01 22:04):private GridMovieAdapter adapter;
    // --Commented out by Inspection (2019-07-24 21:01):private RecyclerView rvCategory;
    private final ArrayList<TvItems> list = new ArrayList<>();

    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tvshow, container, false);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTvies);

        adapter = new TvAdapter(getContext());
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = v.findViewById(R.id.lv_list_tv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        TvAdapter gridTvAdapter = new TvAdapter(getActivity());
        gridTvAdapter.setData(list);
        recyclerView.setAdapter(adapter);

        progressBar = v.findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,android.graphics.PorterDuff.Mode.MULTIPLY);

        mainViewModel.setTvs();

        return v;
    }


    private final Observer<ArrayList<TvItems>> getTvies = new Observer<ArrayList<TvItems>>() {
        @Override
        public void onChanged(ArrayList<TvItems> tvItems) {
            if (tvItems != null) {
                adapter.setData(tvItems);
                showLoading();
            }
        }
    };


    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }



}
