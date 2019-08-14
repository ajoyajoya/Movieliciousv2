package com.ajoyajoya.movieliciousv2.favorite;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.db.TvShowHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvshowFragment extends Fragment implements LoadFavoriteCallback {

    private ProgressBar progressBar;
    private FavoriteTvshowAdapter adapter;
    private TvShowHelper favoriteHelper;

    public FavoriteTvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ((FavoriteActivity) Objects.requireNonNull(getActivity())).setFragmentRefreshListener(new FavoriteActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.detach(FavoriteTvshowFragment.this).attach(FavoriteTvshowFragment.this).commit();

            }
        });

        View v = inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.lv_list_favorite_tv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        favoriteHelper = TvShowHelper.getInstance(getContext());
        favoriteHelper.open();

        progressBar = v.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,android.graphics.PorterDuff.Mode.MULTIPLY);

        adapter = new FavoriteTvshowAdapter(getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        new FavoriteTvshowFragment.LoadFavoriteAsync(favoriteHelper, this).execute();

        return v;
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);

                //System.out.println("Pre EXECUTE Success Execute");
            }
        });

    }

    @Override
    public void postExecute(ArrayList<FavoriteMovie> favoriteTvshow) {
        showLoading();
        adapter.setListFavoriteTvshow(favoriteTvshow);

        //System.out.println("SET LIST FAVORITE Success Execute"+ favoriteTvshow.size() + favoriteTvshow);

    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<FavoriteMovie>> {

        private final WeakReference<TvShowHelper> weakFavoriteHelper;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        LoadFavoriteAsync(TvShowHelper tvshowHelper, LoadFavoriteCallback callback) {
            weakFavoriteHelper = new WeakReference<>(tvshowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
            //System.out.println("Success Pre Execute");
        }

        @Override
        protected ArrayList<FavoriteMovie> doInBackground(Void... voids) {
            //System.out.println("Getting Favorite Movie Data");
            return weakFavoriteHelper.get().getAllFavoriteTvshow();
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteMovie> favoriteTvshow){
            super.onPostExecute(favoriteTvshow);
            weakCallback.get().postExecute(favoriteTvshow);

            //System.out.println("Success Post Execute" + favoriteTvshow.size());

        }


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favoriteHelper.close();
    }

    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
