package com.ajoyajoya.movieliciousv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailMovie extends AppCompatActivity{

    private DetailMovieAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_MOVIE_NAME = "extra_movie_name";
    public static final String EXTRA_TYPE_DETAIL = "extra_type_detail";



    //private RelativeLayout bgMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        setActionBarTitle();

        String movie_type = getIntent().getStringExtra(EXTRA_TYPE_DETAIL);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        switch (movie_type){
            case "moviedetail":
                //Toast.makeText(this, "Detail Movie", Toast.LENGTH_LONG).show();
                showDetailMovie();
                break;
            case "tvshowdetail":
                //Toast.makeText(this, "Detail TV Show", Toast.LENGTH_LONG).show();
                showDetailTv();
                break;
        }



    }

    private void setActionBarTitle(){

        String movie_name = getIntent().getStringExtra(EXTRA_MOVIE_NAME);
        Objects.requireNonNull(getSupportActionBar()).setTitle(movie_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private Observer<ArrayList<DetailMovieItems>> getDetailMovies = new Observer<ArrayList<DetailMovieItems>>() {
        @Override
        public void onChanged(ArrayList<DetailMovieItems> detailMovieItems) {
            if (detailMovieItems != null) {
                adapter.setData(detailMovieItems);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<DetailMovieItems>> getDetailTvies = new Observer<ArrayList<DetailMovieItems>>() {
        @Override
        public void onChanged(ArrayList<DetailMovieItems> detailMovieItems) {
            if (detailMovieItems != null) {
                adapter.setData(detailMovieItems);
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

    private void showDetailMovie(){
        String movie_id = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        System.out.println(movie_id);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDetailMovies().observe(this, getDetailMovies);

        adapter = new DetailMovieAdapter(this);
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.lv_list_detail_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFF673AB7,android.graphics.PorterDuff.Mode.MULTIPLY);

        String movies_id = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        mainViewModel.setDetailMovies(movies_id);
    }

    private void showDetailTv(){
        String movie_id = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        System.out.println(movie_id);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDetailTvies().observe(this, getDetailTvies);

        adapter = new DetailMovieAdapter(this);
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.lv_list_detail_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFF673AB7,android.graphics.PorterDuff.Mode.MULTIPLY);

        String movies_id = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        mainViewModel.setDetailTV(movies_id);
    }


}
