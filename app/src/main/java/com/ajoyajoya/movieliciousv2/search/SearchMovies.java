package com.ajoyajoya.movieliciousv2.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.ajoyajoya.movieliciousv2.MainViewModel;
import com.ajoyajoya.movieliciousv2.R;

import java.util.ArrayList;
import java.util.Objects;

public class SearchMovies extends AppCompatActivity implements View.OnClickListener {

    private SearchResultAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;
    private TextView searchNotFound;
    private RelativeLayout btnCategory;
    private Button btnMovieCat, btnTvShowCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        setActionBarTitle();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnMovieCat = findViewById(R.id.btn_movie_category);
        btnMovieCat.setOnClickListener(this);
        btnTvShowCat = findViewById(R.id.btn_tvshow_category);
        btnTvShowCat.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                if (!query.equals("")){
                    progressBar = findViewById(R.id.progressBar);
                    progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
                    progressBar.setVisibility(View.VISIBLE);

                    mainViewModel = ViewModelProviders.of(SearchMovies.this).get(MainViewModel.class);
                    mainViewModel.getTotalSearchResult().observe(SearchMovies.this, getSearchResult);
                    mainViewModel.getSearchItems().observe(SearchMovies.this, getSearchItems);

                    adapter = new SearchResultAdapter(SearchMovies.this);
                    adapter.notifyDataSetChanged();

                    RecyclerView recyclerView = findViewById(R.id.lv_list_search_result);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchMovies.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);


                    mainViewModel.setTotalSearchResult(query);
                    mainViewModel.setSearch(query, "movie");
                    setMovieSelected();

                }

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(){

        String movie_name = getString(R.string.search_page);
        Objects.requireNonNull(getSupportActionBar()).setTitle(movie_name);
    }

    private final Observer<ArrayList<SearchItems>> getSearchItems = new Observer<ArrayList<SearchItems>>() {
        @Override
        public void onChanged(ArrayList<SearchItems> searchItemsItems) {
            if (searchItemsItems != null) {
                if (searchItemsItems.size()==0){

                    searchNotFound = findViewById(R.id.tv_search_notfound);
                    searchNotFound.setVisibility(View.VISIBLE);
                    RecyclerView recyclerView = findViewById(R.id.lv_list_search_result);
                    recyclerView.setVisibility(View.GONE);
                    showLoading();

                } else {

                    searchNotFound = findViewById(R.id.tv_search_notfound);
                    searchNotFound.setVisibility(View.GONE);

                    adapter.setData(searchItemsItems);
                    showBtnCategory();

                    showLoading();

                }



            }
        }
    };

    private final Observer<ArrayList<String>> getSearchResult = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(@Nullable ArrayList<String> strings) {
            //System.out.println(strings);
            if (strings != null) {
                btnMovieCat = findViewById(R.id.btn_movie_category);
                btnTvShowCat = findViewById(R.id.btn_tvshow_category);
                btnMovieCat.setText("   " + getResources().getString(R.string.tab_text_1) + " (" + strings.get(0) + ")   ");
                btnTvShowCat.setText("   " + getResources().getString(R.string.tab_text_2) + " (" + strings.get(1) + ")   ");

            }
        }
    };

    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showBtnCategory(){
        btnCategory = findViewById(R.id.btn_category);
        btnCategory.setVisibility(View.VISIBLE);
    }

    private void setMovieSelected(){
        btnMovieCat = findViewById(R.id.btn_movie_category);
        btnTvShowCat = findViewById(R.id.btn_tvshow_category);
//        btnMovieCat.setText(R.string.tab_text_1 + resultMovieCat);
//        btnTvShowCat.setText(R.string.tab_text_2 + resultTvCat);
        btnMovieCat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLight)));
        btnMovieCat.setTextColor(getResources().getColor(R.color.colorPrimaryLightest));
        btnMovieCat.setEnabled(false);

        btnTvShowCat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLightest)));
        btnTvShowCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        btnTvShowCat.setEnabled(true);
    }

    private void setTvSelected(){
        btnMovieCat = findViewById(R.id.btn_movie_category);
        btnTvShowCat = findViewById(R.id.btn_tvshow_category);
//        btnMovieCat.setText(R.string.tab_text_1 + resultMovieCat);
//        btnTvShowCat.setText(R.string.tab_text_2 + resultTvCat);
        btnTvShowCat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLight)));
        btnTvShowCat.setTextColor(getResources().getColor(R.color.colorPrimaryLightest));
        btnTvShowCat.setEnabled(false);

        btnMovieCat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLightest)));
        btnMovieCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        btnMovieCat.setEnabled(true);
    }

    @Override
    public void onClick(View v) {


        SearchView searchViewItem = findViewById(R.id.app_bar_search);
        String query = String.valueOf(searchViewItem.getQuery());

        switch (v.getId()) {
            case R.id.btn_movie_category:

                if (!query.equals("")) {

                    progressBar = findViewById(R.id.progressBar);
                    progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
                    progressBar.setVisibility(View.VISIBLE);

                    mainViewModel = ViewModelProviders.of(SearchMovies.this).get(MainViewModel.class);
                    mainViewModel.getSearchItems().observe(SearchMovies.this, getSearchItems);

                    adapter = new SearchResultAdapter(SearchMovies.this);
                    adapter.notifyDataSetChanged();

                    RecyclerView recyclerView = findViewById(R.id.lv_list_search_result);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchMovies.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);


                    mainViewModel.setSearch(query, "movie");

                    setMovieSelected();
                }
                break;

            case R.id.btn_tvshow_category:

                if (!query.equals("")) {
                    progressBar = findViewById(R.id.progressBar);
                    progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
                    progressBar.setVisibility(View.VISIBLE);

                    mainViewModel = ViewModelProviders.of(SearchMovies.this).get(MainViewModel.class);
                    mainViewModel.getSearchItems().observe(SearchMovies.this, getSearchItems);

                    adapter = new SearchResultAdapter(SearchMovies.this);
                    adapter.notifyDataSetChanged();

                    RecyclerView recyclerView1 = findViewById(R.id.lv_list_search_result);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(SearchMovies.this));
                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setVisibility(View.VISIBLE);

                    SearchView searchViewItem1 = findViewById(R.id.app_bar_search);

                    String query1 = String.valueOf(searchViewItem1.getQuery());

                    mainViewModel.setSearch(query1, "tvshow");

                    setTvSelected();
                }
                break;
        }

    }
}
