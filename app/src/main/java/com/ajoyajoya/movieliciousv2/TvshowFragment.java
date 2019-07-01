package com.ajoyajoya.movieliciousv2;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private String[] dataTVName;
    private String[] dataTVRate;
    private String[] dataTVCat;
    private String[] dataTVDesc;
    private TypedArray dataTVPoster;
    // --Commented out by Inspection (2019-07-01 22:04):private GridMovieAdapter adapter;
    private RecyclerView rvCategory;
    private final ArrayList<Movie> list = new ArrayList<>();

    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tvshow, container, false);
        rvCategory = v.findViewById(R.id.lv_list_tv);
        rvCategory.setHasFixedSize(true);

        prepare();
        addItem();

        showRecyclerGrid();


        return v;
    }
    private void addItem() {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < dataTVName.length; i++) {
            Movie movie = new Movie();
            movie.setMoviePoster(dataTVPoster.getResourceId(i, -1));
            movie.setMovieName(dataTVName[i]);
            movie.setMovieRated(dataTVRate[i]);
            movie.setMovieCategory(dataTVCat[i]);
            movie.setMovieDesc(dataTVDesc[i]);

            movies.add(movie);
        }
        list.addAll(movies);
    }

    private void prepare() {
        dataTVName = getResources().getStringArray(R.array.data_tv_name);
        dataTVRate = getResources().getStringArray(R.array.data_tv_rating);
        dataTVCat = getResources().getStringArray(R.array.data_tv_category);
        dataTVDesc = getResources().getStringArray(R.array.data_tv_desc);
        dataTVPoster = getResources().obtainTypedArray(R.array.data_tv_poster);

    }

    private void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridMovieAdapter gridMovieAdapter = new GridMovieAdapter(getActivity());
        gridMovieAdapter.setListTvShow(list);
        rvCategory.setAdapter(gridMovieAdapter);


    }


}
