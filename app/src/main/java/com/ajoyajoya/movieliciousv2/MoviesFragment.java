package com.ajoyajoya.movieliciousv2;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private String[] dataMovieName;
    private String[] dataMovieRate;
    private String[] dataMovieCat;
    private String[] dataMovieDesc;
    private TypedArray dataMoviePoster;
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
        rvCategory = v.findViewById(R.id.lv_list_movie);
        rvCategory.setHasFixedSize(true);

        prepare();
        addItem();

        showRecycleList();

        return v;
    }


    private void addItem() {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < dataMovieName.length; i++) {
            Movie movie = new Movie();
            movie.setMoviePoster(dataMoviePoster.getResourceId(i, -1));
            movie.setMovieName(dataMovieName[i]);
            movie.setMovieRated(dataMovieRate[i]);
            movie.setMovieCategory(dataMovieCat[i]);
            movie.setMovieDesc(dataMovieDesc[i]);

            movies.add(movie);
        }
        list.addAll(movies);
    }

    private void prepare() {
        dataMovieName = getResources().getStringArray(R.array.data_movie_name);
        dataMovieRate = getResources().getStringArray(R.array.data_movie_rating);
        dataMovieCat = getResources().getStringArray(R.array.data_movie_category);
        dataMovieDesc = getResources().getStringArray(R.array.data_movie_desc);
        dataMoviePoster = getResources().obtainTypedArray(R.array.data_movie_poster);

    }

    private void showRecycleList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(getActivity());
        listMovieAdapter.setListMovie(list);
        rvCategory.setAdapter(listMovieAdapter);
    }


}
