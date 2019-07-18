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

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private final Context context;

    private ArrayList<TvItems> mData1 = new ArrayList<>();

    public TvAdapter(Context context) {
        this.context = context;
    }


    public void setData(ArrayList<TvItems> items) {
        mData1.clear();
        mData1.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_movie, viewGroup, false);
        return new TvAdapter.TvViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.TvViewHolder tvViewHolder, final int position) {
        tvViewHolder.bind(mData1.get(position));

        tvViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Kamu Memilih "+  mData1.get(position).getTvId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData1.size();
    }


    public class TvViewHolder extends RecyclerView.ViewHolder {

        final TextView txtTvName;
        final TextView txtTvRating;
        final ImageView imgTvPoster;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTvName = itemView.findViewById(R.id.tv_item_title);
            txtTvRating = itemView.findViewById(R.id.txt_tv_rate);
            imgTvPoster = itemView.findViewById(R.id.img_item_thumbphoto);
        }

        public void bind(TvItems tvItems) {

            String yearsRelease = tvItems.getTvName()+ " ("+ tvItems.getTvReleaseDate().substring(0, 4)+")";
            txtTvName.setText(yearsRelease);

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + tvItems.getTvPoster())
                    .apply(new RequestOptions())
                    .into(imgTvPoster);


            float hasRated = Float.parseFloat(tvItems.getTvRated());


            if (hasRated >= 8.0) {
                txtTvRating.setBackgroundColor(Color.parseColor("#3498db"));
            } else if (hasRated >= 7.0) {
                txtTvRating.setBackgroundColor(Color.parseColor("#2ecc71"));
            } else if (hasRated >= 6.0) {
                txtTvRating.setBackgroundColor(Color.parseColor("#f1c40f"));
            } else if (hasRated >= 5.0) {
                txtTvRating.setBackgroundColor(Color.parseColor("#e67e22"));
            } else {
                txtTvRating.setBackgroundColor(Color.parseColor("#e74c3c"));
            }

            if (hasRated==0.0){
                txtTvRating.setText("NR");
            }else{
                txtTvRating.setText(String.valueOf(hasRated));
            }

        }

    }





}
