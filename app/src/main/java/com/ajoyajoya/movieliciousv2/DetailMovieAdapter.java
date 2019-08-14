package com.ajoyajoya.movieliciousv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailMovieAdapter extends RecyclerView.Adapter<DetailMovieAdapter.DetailMovieViewHolder> {

    private final Context context;

    private final ArrayList<DetailMovieItems> mData3 = new ArrayList<>();

    public DetailMovieAdapter(Context context) {
        this.context = context;
    }



    public void setData(ArrayList<DetailMovieItems> items) {
        mData3.clear();
        mData3.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailMovieAdapter.DetailMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_movie, viewGroup, false);
        return new DetailMovieAdapter.DetailMovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailMovieAdapter.DetailMovieViewHolder detailMovieViewHolder, int position) {
        detailMovieViewHolder.bind(mData3.get(position));

        detailMovieViewHolder.itemView.findViewById(R.id.btn_trailer_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  mData.get(position).getMovieId(), Toast.LENGTH_SHORT).show();

                Intent moveIntent = new Intent(context, PopupVideo.class);
                moveIntent.putExtra(PopupVideo.EXTRA_VIDEO_ID, String.valueOf(mData3.get(detailMovieViewHolder.getAdapterPosition()).getVideoTrailer()));
                moveIntent.putExtra(PopupVideo.EXTRA_VIDEO_SOURCE, String.valueOf(mData3.get(detailMovieViewHolder.getAdapterPosition()).getVideoSource()));
                context.startActivity(moveIntent);

            }
        });

        detailMovieViewHolder.itemView.findViewById(R.id.img_trailer_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Kamu Memilih "+  mData.get(position).getMovieId(), Toast.LENGTH_SHORT).show();

                Intent moveIntent = new Intent(context, PopupVideo.class);
                moveIntent.putExtra(PopupVideo.EXTRA_VIDEO_ID, String.valueOf(mData3.get(detailMovieViewHolder.getAdapterPosition()).getVideoTrailer()));
                moveIntent.putExtra(PopupVideo.EXTRA_VIDEO_SOURCE, String.valueOf(mData3.get(detailMovieViewHolder.getAdapterPosition()).getVideoSource()));
                context.startActivity(moveIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData3.size();
    }

    public class DetailMovieViewHolder extends RecyclerView.ViewHolder {

        final TextView tvMovieName;
        final TextView tvMovieRate;
        final TextView tvMovieCat;
        final TextView tvMovieDesc;
        final ImageView imgMoviePoster;
        final ImageView imgTrailerLink;
        final RelativeLayout bgMovieDetail;
        final TextView tvMovieCrew1;
        final TextView tvMovieCrew2;
        final TextView tvMovieJob1;
        final TextView tvMovieJob2;


        DetailMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.txt_movie_name);
            tvMovieRate = itemView.findViewById(R.id.txt_movie_rate);
            tvMovieCat = itemView.findViewById(R.id.txt_movie_cat);
            tvMovieDesc = itemView.findViewById(R.id.txt_movie_desc);
            imgMoviePoster = itemView.findViewById(R.id.img_poster_movie);
            imgTrailerLink = itemView.findViewById(R.id.img_trailer_link);
            bgMovieDetail = itemView.findViewById(R.id.bg_movie_detail);
            tvMovieCrew1 = itemView.findViewById(R.id.txt_movie_crew_name1);
            tvMovieCrew2 = itemView.findViewById(R.id.txt_movie_crew_name2);
            tvMovieJob1 = itemView.findViewById(R.id.txt_movie_crew_job1);
            tvMovieJob2 = itemView.findViewById(R.id.txt_movie_crew_job2);
        }

        void bind(DetailMovieItems detailMovieItems) {

            String yearsRelease = detailMovieItems.getMovieName()+ " ("+ detailMovieItems.getReleaseDate().substring(0, 4)+")";
            tvMovieName.setText(yearsRelease);

            tvMovieCat.setText(detailMovieItems.getStringBuilder().toString());
            tvMovieDesc.setText(detailMovieItems.getMovieDesc());

            String urlBackdrop = "https://image.tmdb.org/t/p/original" +detailMovieItems.getMoviePoster();

            Glide.with(context).load(urlBackdrop).into(imgMoviePoster);
            Glide.with(context).load(urlBackdrop).into(imgTrailerLink);

            //noinspection deprecation
            Glide.with(context)
                    .load(urlBackdrop)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 7)))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            bgMovieDetail.setBackground(resource);
                        }
                    });

            float hasRated = Float.parseFloat(detailMovieItems.getMovieRated());


            if (hasRated >= 8.0) {
                tvMovieRate.setTextColor(Color.parseColor("#3498db"));
            } else if (hasRated >= 7.0) {
                tvMovieRate.setTextColor(Color.parseColor("#2ecc71"));
            } else if (hasRated >= 6.0) {
                tvMovieRate.setTextColor(Color.parseColor("#f1c40f"));
            } else if (hasRated >= 5.0) {
                tvMovieRate.setTextColor(Color.parseColor("#e67e22"));
            } else {
                tvMovieRate.setTextColor(Color.parseColor("#e74c3c"));
            }

            if (hasRated==0.0){
                tvMovieRate.setText(R.string.not_rated);
            }else{
                tvMovieRate.setText(String.valueOf(hasRated));
            }

            tvMovieCrew2.setText(detailMovieItems.getCrew1());
            tvMovieJob1.setText(detailMovieItems.getJob1());
            tvMovieCrew1.setText(detailMovieItems.getCrew2());
            tvMovieJob2.setText(detailMovieItems.getJob2());


        }
    }
}
