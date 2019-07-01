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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.GridViewHolder> {

    private final Context context;

    private ArrayList<Movie> listTvShow;

    private ArrayList<Movie> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(ArrayList<Movie> listTvShow) {
        this.listTvShow = listTvShow;
    }


    public GridMovieAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_movie, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder gridViewHolder, final int position) {
        gridViewHolder.txtMovieName.setText(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieName());
        gridViewHolder.txtMovieDescription.setText(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieDesc());
        gridViewHolder.txtMovieCategory.setText(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieCategory());
        gridViewHolder.txtMovieRating.setText(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieRated());

        Glide.with(context)
                .load(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMoviePoster())
                .apply(new RequestOptions())
                .into(gridViewHolder.imgMoviePoster);


        float backgroundRating = Float.parseFloat(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieRated());

        if (backgroundRating>=8.0){
            gridViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#3498db"));
        } else if (backgroundRating>=7.0){
            gridViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#2ecc71"));
        } else if (backgroundRating>=6.0){
            gridViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#f1c40f"));
        } else if (backgroundRating>=5.0){
            gridViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e67e22"));
        } else {
            gridViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e74c3c"));
        }

        gridViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  getListTvShow().get(position).getMovieName(), Toast.LENGTH_SHORT).show();

                Movie movie = new Movie();
                movie.setMovieName(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieName());
                movie.setMovieRated(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieRated());
                movie.setMovieCategory(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieCategory());
                movie.setMovieDesc(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMovieDesc());
                movie.setMoviePoster(getListTvShow().get(gridViewHolder.getAdapterPosition()).getMoviePoster());

                Intent moveIntent = new Intent(context, DetailMovie.class);
                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE, movie);
                context.startActivity(moveIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return getListTvShow().size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        final TextView txtMovieName;
        final TextView txtMovieRating;
        final TextView txtMovieDescription;
        final TextView txtMovieCategory;
        final ImageView imgMoviePoster;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.tv_item_title);
            txtMovieRating = itemView.findViewById(R.id.txt_tv_rate);
            txtMovieCategory = itemView.findViewById(R.id.txt_tv_cat);
            txtMovieDescription = itemView.findViewById(R.id.txt_tv_desc);
            imgMoviePoster = itemView.findViewById(R.id.img_item_thumbphoto);
        }
    }
}
