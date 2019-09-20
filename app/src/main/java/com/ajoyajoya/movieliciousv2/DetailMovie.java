package com.ajoyajoya.movieliciousv2;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajoyajoya.movieliciousv2.db.MovieHelper;
import com.ajoyajoya.movieliciousv2.db.TvShowHelper;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovieAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DetailMovie extends AppCompatActivity{

    private DetailMovieAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    private FavoriteMovieAdapter adapterFavorite;

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_MOVIE_NAME = "extra_movie_name";
    public static final String EXTRA_TYPE_DETAIL = "extra_type_detail";
    public static final String EXTRA_POSITION = "extra_position";

    private MenuItem favoriteMenu;
    private Boolean isfavoriteMenu = false;

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final int REQUEST_DELETE = 300;
    public static final int RESULT_DELETE = 301;
    public static int RESULT_MOVING = 0;

    private FavoriteMovie favoriteMovie;
    private int position;

    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;


    //private RelativeLayout bgMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        setActionBarTitle();

        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);

        //String movie_type = getIntent().getStringExtra(EXTRA_TYPE_DETAIL);
        String movie_type = favoriteMovie.getTypedetail();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        switch (movie_type){
            case "moviedetail":
                //Toast.makeText(this, "Detail Movie", Toast.LENGTH_LONG).show();

                movieHelper = MovieHelper.getInstance(getApplicationContext());
                movieHelper.open();
                showDetailMovie();

                break;
            case "tvshowdetail":
                //Toast.makeText(this, "Detail TV Show", Toast.LENGTH_LONG).show();

                tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
                tvShowHelper.open();
                showDetailTv();

                break;
        }





    }


    private void setActionBarTitle(){
        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);

        String movie_name = favoriteMovie.getMoviename();
        int movie_position = favoriteMovie.getPosition_list();
        Objects.requireNonNull(getSupportActionBar()).setTitle(movie_name);
    }



    private final Observer<ArrayList<DetailMovieItems>> getDetailMovies = new Observer<ArrayList<DetailMovieItems>>() {
        @Override
        public void onChanged(ArrayList<DetailMovieItems> detailMovieItems) {
            if (detailMovieItems != null) {
                adapter.setData(detailMovieItems);
                showLoading();
            }
        }
    };

    private final Observer<ArrayList<DetailMovieItems>> getDetailTvies = new Observer<ArrayList<DetailMovieItems>>() {
        @Override
        public void onChanged(ArrayList<DetailMovieItems> detailMovieItems) {
            if (detailMovieItems != null) {
                adapter.setData(detailMovieItems);
                showLoading();
            }
        }
    };


    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showDetailMovie(){
        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);
        String movie_id = String.valueOf(favoriteMovie.getMovieid());
        //System.out.println(movie_id);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDetailMovies().observe(this, getDetailMovies);

        adapter = new DetailMovieAdapter(this);
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.lv_list_detail_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFF673AB7,android.graphics.PorterDuff.Mode.MULTIPLY);

        String movies_id = String.valueOf(favoriteMovie.getMovieid());
        mainViewModel.setDetailMovies(movies_id);
    }

    private void showDetailTv(){
        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);
        String movie_id = String.valueOf(favoriteMovie.getMovieid());
        //System.out.println(movie_id);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDetailTvies().observe(this, getDetailTvies);

        adapter = new DetailMovieAdapter(this);
        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.lv_list_detail_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFF673AB7,android.graphics.PorterDuff.Mode.MULTIPLY);

        String movies_id = String.valueOf(favoriteMovie.getMovieid());
        mainViewModel.setDetailTV(movies_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String movie_type = favoriteMovie.getTypedetail();
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        favoriteMenu = menu.findItem(R.id.action_favorite);

        switch (movie_type){
            case "moviedetail":
                Boolean isFavorited = movieHelper.getSingleFavoriteMovies(String.valueOf(favoriteMovie.getMovieid()));
                System.out.println(isFavorited);
                if (isFavorited){
                    favoriteMenu.setIcon(R.drawable.ic_favorite_red_24dp);
                    isfavoriteMenu = true;
                } else {
                    favoriteMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    isfavoriteMenu = false;
                }
                break;
            case "tvshowdetail":

                Boolean isTvFavorited = tvShowHelper.getSingleFavoriteTvShow(String.valueOf(favoriteMovie.getMovieid()));
                if (isTvFavorited){
                    favoriteMenu.setIcon(R.drawable.ic_favorite_red_24dp);
                    isfavoriteMenu = true;
                } else {
                    favoriteMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    isfavoriteMenu = false;
                }
                break;
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            if(isTaskRoot()){
                startActivity(new Intent(DetailMovie.this,MainActivity.class));
                // using finish() is optional, use it if you do not want to keep currentActivity in stack
                finish();
            }else{
                super.onBackPressed();
            }

            finish(); // close this activity and return to preview activity (if there is any)
        }

        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int selectedMode) {
        if (selectedMode == R.id.action_favorite) {
            String movie_type = favoriteMovie.getTypedetail();

            switch (movie_type) {
                case "moviedetail":

                    if (!isfavoriteMenu) {

                        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);
                        favoriteMovie.setDate(getCurrentDate());

                        //System.out.println(idMovie + moviName + imgUrl + curDate);

                        long result = movieHelper.insertFavoriteMovie(favoriteMovie);

                        if (result > 0) {
                            favoriteMovie.setId((int) result);
                            Toast.makeText(DetailMovie.this, R.string.success_favorite, Toast.LENGTH_SHORT).show();
                            favoriteMenu.setIcon(R.drawable.ic_favorite_red_24dp);
                            isfavoriteMenu = true;
                        } else {
                            Toast.makeText(DetailMovie.this, R.string.failed_favorite, Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setTitle(R.string.dialog_title);
                        alertDialogBuilder
                                .setMessage(R.string.dialog_message)
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes_remove, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Toast.makeText(context, context.getString(R.string.success_unfavorite) + " " + listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getId() +" "+ listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();

                                        movieHelper = new MovieHelper(getApplicationContext());

                                        int unFavoriteId = favoriteMovie.getMovieid();
                                        System.out.println(unFavoriteId);
                                        long result = movieHelper.deleteFavoriteSingleMovie(unFavoriteId);
                                        if (result > 0) {
                                            Toast.makeText(DetailMovie.this, getString(R.string.success_unfavorite) + " " + favoriteMovie.getMoviename(), Toast.LENGTH_SHORT).show();
                                            favoriteMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
                                            isfavoriteMenu = false;
                                        } else {
                                            Toast.makeText(DetailMovie.this, getString(R.string.failed_unfavorite) + " " + favoriteMovie.getMoviename(), Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                })
                                .setNegativeButton(R.string.no_remove, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }


                    break;
                case "tvshowdetail":

                    if (!isfavoriteMenu) {

                        favoriteMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);
                        favoriteMovie.setDate(getCurrentDate());

                        //System.out.println(idMovie + moviName + imgUrl + curDate);

                        long result = tvShowHelper.insertFavoriteTv(favoriteMovie);

                        if (result > 0) {
                            favoriteMovie.setId((int) result);
                            Toast.makeText(DetailMovie.this, R.string.success_favorite, Toast.LENGTH_SHORT).show();
                            favoriteMenu.setIcon(R.drawable.ic_favorite_red_24dp);
                            isfavoriteMenu = true;
                        } else {
                            Toast.makeText(DetailMovie.this, R.string.failed_favorite, Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setTitle(R.string.dialog_title);
                        alertDialogBuilder
                                .setMessage(R.string.dialog_message)
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes_remove, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Toast.makeText(context, context.getString(R.string.success_unfavorite) + " " + listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getId() +" "+ listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();

                                        tvShowHelper = new TvShowHelper(getApplicationContext());

                                        int unFavoriteId = favoriteMovie.getMovieid();
                                        long result = tvShowHelper.deleteFavoriteSingleTv(unFavoriteId);
                                        if (result > 0) {
                                            Toast.makeText(DetailMovie.this, getString(R.string.success_unfavorite) + " " + favoriteMovie.getMoviename(), Toast.LENGTH_SHORT).show();
                                            favoriteMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
                                            isfavoriteMenu = false;
                                        } else {
                                            Toast.makeText(DetailMovie.this, getString(R.string.failed_unfavorite) + " " + favoriteMovie.getMoviename(), Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                })
                                .setNegativeButton(R.string.no_remove, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    break;
            }
        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    public void onBackPressed() {

        if(isTaskRoot()){
            startActivity(new Intent(DetailMovie.this,MainActivity.class));
            // using finish() is optional, use it if you do not want to keep currentActivity in stack
            finish();
        }else{
            super.onBackPressed();
        }

    }


}
