//package com.ajoyajoya.movieliciousv2;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//
//import java.util.ArrayList;
//
//public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CategoryViewHolder> {
//
//    private final Context context;
//    private ArrayList<Movie> listMovie;
//
//    public ListMovieAdapter(Context context) {
//        this.context = context;
//    }
//
//    private ArrayList<Movie> getListMovie() {
//        return listMovie;
//    }
//
//    public void setListMovie(ArrayList<Movie> listMovie) {
//        this.listMovie = listMovie;
//    }
//
//    @NonNull
//    @Override
//    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
//        return new CategoryViewHolder(itemRow);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int position) {
//        categoryViewHolder.txtMovieName.setText(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieName());
//        categoryViewHolder.txtMovieDescription.setText(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieDesc());
//        categoryViewHolder.txtMovieCategory.setText(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieCategory());
//        categoryViewHolder.txtMovieRating.setText(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieRated());
//
//        categoryViewHolder.getAdapterPosition();
//
//        Glide.with(context)
//                .load(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMoviePoster())
//                .apply(new RequestOptions())
//                .into(categoryViewHolder.imgMoviePoster);
//
//
//        float backgroundRating = Float.parseFloat(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieRated());
//
//        if (backgroundRating>=8.0){
//            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#3498db"));
//        } else if (backgroundRating>=7.0){
//            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#2ecc71"));
//        } else if (backgroundRating>=6.0){
//            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#f1c40f"));
//        } else if (backgroundRating>=5.0){
//            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e67e22"));
//        } else {
//            categoryViewHolder.txtMovieRating.setBackgroundColor(Color.parseColor("#e74c3c"));
//        }
//
//        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "Kamu Memilih "+  getListMovie().get(position).getMovieName(), Toast.LENGTH_SHORT).show();
//
//                Movie movie = new Movie();
//                movie.setMovieName(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieName());
//                movie.setMovieRated(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieRated());
//                movie.setMovieCategory(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieCategory());
//                movie.setMovieDesc(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMovieDesc());
//                movie.setMoviePoster(getListMovie().get(categoryViewHolder.getAdapterPosition()).getMoviePoster());
//
//                Intent moveIntent = new Intent(context, DetailMovie.class);
//                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE, movie);
//                context.startActivity(moveIntent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return getListMovie().size();
//    }
//
//    public class CategoryViewHolder extends RecyclerView.ViewHolder {
//        final TextView txtMovieName;
//        final TextView txtMovieRating;
//        final TextView txtMovieDescription;
//        final TextView txtMovieCategory;
//        final ImageView imgMoviePoster;
//
//        CategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtMovieName = itemView.findViewById(R.id.txt_movie_name);
//            txtMovieRating = itemView.findViewById(R.id.txt_movie_rate);
//            txtMovieCategory = itemView.findViewById(R.id.txt_movie_cat);
//            txtMovieDescription = itemView.findViewById(R.id.txt_movie_desc);
//            imgMoviePoster = itemView.findViewById(R.id.img_poster_movie);
//        }
//    }
//
//}
