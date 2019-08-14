package com.ajoyajoya.movieliciousv2.favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajoyajoya.movieliciousv2.DetailMovie;
import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.db.MovieHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {


    private final Context context;

    private final ArrayList<FavoriteMovie> listFavoriteMovie = new ArrayList<>();

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<FavoriteMovie> getListFavoriteMovie() {
        return listFavoriteMovie;
    }

    private MovieHelper movieHelper;

    public void setListFavoriteMovie(ArrayList<FavoriteMovie> listFavoriteMovie) {
        if (listFavoriteMovie.size() > 0) {
            this.listFavoriteMovie.clear();
        }
        this.listFavoriteMovie.addAll(listFavoriteMovie);
        notifyDataSetChanged();
    }

    public void addItem(FavoriteMovie favoriteMovie) {
        this.listFavoriteMovie.add(0, favoriteMovie);
        notifyItemInserted(0);
    }

    private void removeItem(int position) {
        this.listFavoriteMovie.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFavoriteMovie.size());
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_movie, viewGroup, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieAdapter.FavoriteMovieViewHolder favoriteMovieViewHolder, int position) {
        favoriteMovieViewHolder.bind(listFavoriteMovie.get(position));

        favoriteMovieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  mData.get(position).getMovieId(), Toast.LENGTH_SHORT).show();

                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setMovieid(listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMovieid());
                favoriteMovie.setMoviename(listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename());
                favoriteMovie.setImageurl(listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getImageurl());
                favoriteMovie.setTypedetail("moviedetail");
                favoriteMovie.setPosition_list(favoriteMovieViewHolder.getAdapterPosition());
                System.out.println(favoriteMovieViewHolder.getAdapterPosition());
                Intent moveIntent = new Intent(context, DetailMovie.class);
                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                context.startActivity(moveIntent);

            }
        });

        Button deleteFavorite = favoriteMovieViewHolder.itemView.findViewById(R.id.delete_favorite);
        deleteFavorite.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.delete_favorite) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(R.string.dialog_title);
                    alertDialogBuilder
                            .setMessage(R.string.dialog_message)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes_remove, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Toast.makeText(context, context.getString(R.string.success_unfavorite) + " " + listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getId() +" "+ listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();

                                    movieHelper = new MovieHelper(context);

                                    int unFavoriteId = listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getId();
                                    long result = movieHelper.deleteFavoriteMovie(unFavoriteId);
                                    if (result > 0) {
                                        Toast.makeText(context, context.getString(R.string.success_unfavorite) + " " + listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();
                                        removeItem(favoriteMovieViewHolder.getAdapterPosition());
                                    } else {
                                        //Toast.makeText(context, context.getString(R.string.failed_unfavorite) +" "+ listFavoriteMovie.get(favoriteMovieViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();
                                        removeItem(favoriteMovieViewHolder.getAdapterPosition());
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

            }
        });



    }

    @Override
    public int getItemCount() {

        return listFavoriteMovie.size();
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {

        final TextView txtMovieName;
        final ImageView imgMoviePoster;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_favoritemovie_name);
            imgMoviePoster = itemView.findViewById(R.id.img_poster_favoritemovie);
        }

        void bind(FavoriteMovie favoriteMovie) {

            txtMovieName.setText(favoriteMovie.getMoviename());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + favoriteMovie.getImageurl())
                    .apply(new RequestOptions())
                    .into(imgMoviePoster);
        }
    }


}
