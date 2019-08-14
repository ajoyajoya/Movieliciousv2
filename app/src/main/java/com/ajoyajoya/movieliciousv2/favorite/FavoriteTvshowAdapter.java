package com.ajoyajoya.movieliciousv2.favorite;

import android.app.Activity;
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
import com.ajoyajoya.movieliciousv2.db.TvShowHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.FavoriteTvshowViewHolder>  {
    private final Context context;

    private final ArrayList<FavoriteMovie> listFavoriteTvshow = new ArrayList<>();

    private Activity activity;

    private TvShowHelper tvShowHelper;

    public FavoriteTvshowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<FavoriteMovie> getListFavoriteTvshow() {
        return listFavoriteTvshow;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setListFavoriteTvshow(ArrayList<FavoriteMovie> listFavoriteTvshow) {
        if (listFavoriteTvshow.size() > 0) {
            this.listFavoriteTvshow.clear();
        }
        this.listFavoriteTvshow.addAll(listFavoriteTvshow);
        notifyDataSetChanged();
    }

    public void addItem(FavoriteMovie favoriteMovie) {
        this.listFavoriteTvshow.add(0, favoriteMovie);
        notifyItemInserted(0);
    }


    private void removeItem(int position) {
        this.listFavoriteTvshow.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFavoriteTvshow.size());
    }

    @NonNull
    @Override
    public FavoriteTvshowAdapter.FavoriteTvshowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_tv, viewGroup, false);
        return new FavoriteTvshowAdapter.FavoriteTvshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteTvshowAdapter.FavoriteTvshowViewHolder favoriteTvshowViewHolder, int position) {

        favoriteTvshowViewHolder.bind(listFavoriteTvshow.get(position));

        favoriteTvshowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  mData1.get(position).getTvId(), Toast.LENGTH_SHORT).show();
                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setMovieid(listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getMovieid());
                favoriteMovie.setMoviename(listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getMoviename());
                favoriteMovie.setImageurl(listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getImageurl());
                favoriteMovie.setTypedetail("tvshowdetail");
                Intent moveIntent = new Intent(context, DetailMovie.class);
                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                context.startActivity(moveIntent);
            }
        });

        Button deleteFavorite = favoriteTvshowViewHolder.itemView.findViewById(R.id.delete_favoritetv);
        deleteFavorite.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.delete_favoritetv) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(R.string.dialog_title);
                    alertDialogBuilder
                            .setMessage(R.string.dialog_message)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes_remove, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    tvShowHelper = new TvShowHelper(context);

                                    int unFavoriteId = listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getId();

                                    long result = tvShowHelper.deleteFavoriteTv(unFavoriteId);
                                    if (result > 0) {
                                        Toast.makeText(context, context.getString(R.string.success_unfavorite) + " " + listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();
                                        removeItem(favoriteTvshowViewHolder.getAdapterPosition());
                                    } else {
                                        Toast.makeText(context, context.getString(R.string.failed_unfavorite) + " " + listFavoriteTvshow.get(favoriteTvshowViewHolder.getAdapterPosition()).getMoviename(), Toast.LENGTH_SHORT).show();

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
        return listFavoriteTvshow.size();
    }

    public class FavoriteTvshowViewHolder extends RecyclerView.ViewHolder {

        final TextView txtTvshowName;
        final ImageView imgTvshowPoster;

        FavoriteTvshowViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTvshowName = itemView.findViewById(R.id.txt_favoritetv_name);
            imgTvshowPoster = itemView.findViewById(R.id.img_poster_favoritetv);
        }

        void bind(FavoriteMovie favoriteMovie) {
            txtTvshowName.setText(favoriteMovie.getMoviename());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + favoriteMovie.getImageurl())
                    .apply(new RequestOptions())
                    .into(imgTvshowPoster);
        }
    }
}
