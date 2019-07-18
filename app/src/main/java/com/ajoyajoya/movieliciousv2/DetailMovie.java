package com.ajoyajoya.movieliciousv2;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailMovie extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private ScrollView bgMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        setActionBarTitle();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView tvMovieName = findViewById(R.id.txt_movie_name);
        TextView tvMovieRate = findViewById(R.id.txt_movie_rate);
        TextView tvMovieCat = findViewById(R.id.txt_movie_cat);
        TextView tvMovieDesc = findViewById(R.id.txt_movie_desc);
        ImageView imgMoviePoster = findViewById(R.id.img_poster_movie);
        ImageView imgTrailerLink = findViewById(R.id.img_trailer_link);
        bgMovieDetail = findViewById(R.id.bg_movie_detail);

        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        tvMovieName.setText(movie.getMovieName());
        tvMovieRate.setText(movie.getMovieRated());
        tvMovieCat.setText(movie.getMovieCategory());
        tvMovieDesc.setText(movie.getMovieDesc());

        Glide.with(this).load(movie.getMoviePoster()).into(imgMoviePoster);
        Glide.with(this).load(movie.getMoviePoster()).into(imgTrailerLink);

        //noinspection deprecation
        Glide.with(this).load(movie.getMoviePoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 3)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        bgMovieDetail.setBackground(resource);
                    }
                });

        float backgroundRating = Float.parseFloat(movie.getMovieRated());

        if (backgroundRating>=8.0){
            tvMovieRate.setTextColor(Color.parseColor("#3498db"));
        } else if (backgroundRating>=7.0){
            tvMovieRate.setTextColor(Color.parseColor("#2ecc71"));
        } else if (backgroundRating>=6.0){
            tvMovieRate.setTextColor(Color.parseColor("#f1c40f"));
        } else if (backgroundRating>=5.0){
            tvMovieRate.setTextColor(Color.parseColor("#e67e22"));
        } else {
            tvMovieRate.setTextColor(Color.parseColor("#e74c3c"));
        }



    }

    private void setActionBarTitle(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail Movie");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
