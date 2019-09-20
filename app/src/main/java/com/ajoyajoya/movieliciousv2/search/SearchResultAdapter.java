package com.ajoyajoya.movieliciousv2.search;

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

import com.ajoyajoya.movieliciousv2.DetailMovie;
import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private final Context context;

    private final ArrayList<SearchItems> mData4 = new ArrayList<>();

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SearchItems> items) {
        mData4.clear();
        mData4.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new SearchResultAdapter.SearchResultViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchResultViewHolder searchResultViewHolder, int position) {
        searchResultViewHolder.bind(mData4.get(position));

        final String categoryTypeMovie = mData4.get(searchResultViewHolder.getAdapterPosition()).getCategoryType();
        final FavoriteMovie favoriteMovie = new FavoriteMovie();



        searchResultViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (categoryTypeMovie){
                    case "movie":
                        favoriteMovie.setMovieid(mData4.get(searchResultViewHolder.getAdapterPosition()).getMovieId());
                        favoriteMovie.setMoviename(mData4.get(searchResultViewHolder.getAdapterPosition()).getMovieName());
                        favoriteMovie.setImageurl(mData4.get(searchResultViewHolder.getAdapterPosition()).getMoviePoster());
                        favoriteMovie.setTypedetail("moviedetail");
                        Intent moveIntent = new Intent(context, DetailMovie.class);
                        moveIntent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                        context.startActivity(moveIntent);
                        break;
                    case "tvshow":
                        favoriteMovie.setMovieid(mData4.get(searchResultViewHolder.getAdapterPosition()).getMovieId());
                        favoriteMovie.setMoviename(mData4.get(searchResultViewHolder.getAdapterPosition()).getMovieName());
                        favoriteMovie.setImageurl(mData4.get(searchResultViewHolder.getAdapterPosition()).getMoviePoster());
                        favoriteMovie.setTypedetail("tvshowdetail");
                        Intent moveIntent2 = new Intent(context, DetailMovie.class);
                        moveIntent2.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                        context.startActivity(moveIntent2);
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData4.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {

        final TextView txtMovieName;
        final TextView txtMovieRating;
        final TextView txtMovieDescription;
        final TextView txtMovieCategory;
        final ImageView imgMoviePoster;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_movie_name);
            txtMovieRating = itemView.findViewById(R.id.txt_movie_rate);
            txtMovieCategory = itemView.findViewById(R.id.txt_movie_cat);
            txtMovieDescription = itemView.findViewById(R.id.txt_movie_desc);
            imgMoviePoster = itemView.findViewById(R.id.img_poster_movie);
        }

        public void bind(SearchItems searchItems) {
            String release = context.getString(R.string.date_release);

            String yearsRelease = searchItems.getMovieName();

            String releaseDate = release +" : " + searchItems.getReleaseDate();

            txtMovieName.setText(yearsRelease);
            txtMovieCategory.setText(releaseDate);
            txtMovieDescription.setText(searchItems.getMovieDesc());

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + searchItems.getMoviePoster())
                    .apply(new RequestOptions())
                    .into(imgMoviePoster);


            float hasRated = Float.parseFloat(searchItems.getMovieRated());


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
