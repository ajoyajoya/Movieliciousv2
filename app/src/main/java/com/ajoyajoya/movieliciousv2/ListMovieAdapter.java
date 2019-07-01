package com.ajoyajoya.movieliciousv2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<Movie> listMovie;

    public ListMovieAdapter(Context context) {
        this.context = context;
    }


    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.txtMovieName.setText(getListMovie().get(position).getMovieName());
        categoryViewHolder.txtMovieDescription.setText(getListMovie().get(position).getMovieDesc());
        categoryViewHolder.txtMovieCategory.setText(getListMovie().get(position).getMovieCategory());
        categoryViewHolder.txtMovieRating.setText(getListMovie().get(position).getMovieRated());

        Glide.with(context)
                .load(getListMovie().get(position).getMoviePoster())
                .apply(new RequestOptions())
                .into(categoryViewHolder.imgMoviePoster);


        float backgroundRating = Float.parseFloat(getListMovie().get(position).getMovieRated());

        if (backgroundRating>=8.0){
            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#3498db"));
        } else if (backgroundRating>=7.0){
            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#2ecc71"));
        } else if (backgroundRating>=6.0){
            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#f1c40f"));
        } else if (backgroundRating>=5.0){
            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e67e22"));
        } else {
            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e74c3c"));
        }

        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Kamu Memilih "+  getListMovie().get(position).getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtMovieName;
        TextView txtMovieRating;
        TextView txtMovieDescription;
        TextView txtMovieCategory;
        ImageView imgMoviePoster;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_movie_name);
            txtMovieRating = itemView.findViewById(R.id.txt_movie_rate);
            txtMovieCategory = itemView.findViewById(R.id.txt_movie_cat);
            txtMovieDescription = itemView.findViewById(R.id.txt_movie_desc);
            imgMoviePoster = itemView.findViewById(R.id.img_poster_movie);
        }
    }

}
