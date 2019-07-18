package com.ajoyajoya.movieliciousv2;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    // --Commented out by Inspection (2019-07-01 22:07):private ListMovieAdapter adapter;
    private RecyclerView rvCategory;
    private final ArrayList<Movie> list = new ArrayList<>();


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_movies, container, false);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, getMovies);

        adapter = new MovieAdapter(getContext());
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = v.findViewById(R.id.lv_list_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //edtCity = v.findViewById(R.id.editCity);
        progressBar = v.findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,android.graphics.PorterDuff.Mode.MULTIPLY);

        String movies = "";
        mainViewModel.setMovies(movies);
        String genres = "";

        return v;
    }

    private Observer<ArrayList<MovieItems>> getMovies = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                adapter.setData(movieItems);
                showLoading(false);
            }
        }
    };


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }





}
