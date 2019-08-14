package com.ajoyajoya.movieliciousv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  {

    private final Context context;

    private final ArrayList<MovieItems> mData = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mData.get(position));

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  mData.get(position).getMovieId(), Toast.LENGTH_SHORT).show();

                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setMovieid(mData.get(movieViewHolder.getAdapterPosition()).getMovieId());
                favoriteMovie.setMoviename(mData.get(movieViewHolder.getAdapterPosition()).getMovieName());
                favoriteMovie.setImageurl(mData.get(movieViewHolder.getAdapterPosition()).getMoviePoster());
                favoriteMovie.setTypedetail("moviedetail");
                Intent moveIntent = new Intent(context, DetailMovie.class);
                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                context.startActivity(moveIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {


        final TextView txtMovieName;
        final TextView txtMovieRating;
        final TextView txtMovieDescription;
        final TextView txtMovieCategory;
        final ImageView imgMoviePoster;

        MovieViewHolder(@NonNull View itemView){
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_movie_name);
            txtMovieRating = itemView.findViewById(R.id.txt_movie_rate);
            txtMovieCategory = itemView.findViewById(R.id.txt_movie_cat);
            txtMovieDescription = itemView.findViewById(R.id.txt_movie_desc);
            imgMoviePoster = itemView.findViewById(R.id.img_poster_movie);
        }

        void bind(MovieItems movieItems) {

            String release = context.getString(R.string.date_release);

            String yearsRelease = movieItems.getMovieName()+ " ("+ movieItems.getReleaseDate().substring(0, 4)+")";

            String releaseDate = release +" : " + movieItems.getReleaseDate();

            txtMovieName.setText(yearsRelease);
            txtMovieCategory.setText(releaseDate);
            txtMovieDescription.setText(movieItems.getMovieDesc());

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + movieItems.getMoviePoster())
                    .apply(new RequestOptions())
                    .into(imgMoviePoster);


            float hasRated = Float.parseFloat(movieItems.getMovieRated());


            if (hasRated >= 8.0) {
                txtMovieRating.setBackgroundColor(Color.parseColor("#3498db"));
            } else if (hasRated >= 7.0) {
                txtMovieRating.setBackgroundColor(Color.parseColor("#2ecc71"));
            } else if (hasRated >= 6.0) {
                txtMovieRating.setBackgroundColor(Color.parseColor("#f1c40f"));
            } else if (hasRated >= 5.0) {
                txtMovieRating.setBackgroundColor(Color.parseColor("#e67e22"));
            } else {
                txtMovieRating.setBackgroundColor(Color.parseColor("#e74c3c"));
            }

            if (hasRated==0.0){
                txtMovieRating.setText(R.string.not_rated);
            }else{
                txtMovieRating.setText(String.valueOf(hasRated));
            }





        }


    }

}
