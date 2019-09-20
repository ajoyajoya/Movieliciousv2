package com.ajoyajoya.movieliciousv2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ajoyajoya.movieliciousv2.search.SearchItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MainViewModel extends ViewModel {

    private static final String API_KEY = "6c850abf79ae2a311643afba9e9ff654";
    private final MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<TvItems>> listTv = new MutableLiveData<>();


    private final MutableLiveData<ArrayList<DetailMovieItems>> listDetailMovies = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<DetailMovieItems>> listDetailTvies = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<SearchItems>> listsSearchItems = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<String>> listTotalResult = new MutableLiveData<>();

    private String languageID = "";


    void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItems> listItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language="+languageID;
        //System.out.println(url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    void setTvs() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvItems> listItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key="+API_KEY+"&language="+languageID;
        //System.out.println(url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvies = list.getJSONObject(i);
                        TvItems tvItems = new TvItems(tvies);
                        listItems.add(tvItems);
                    }
                    listTv.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    void setDetailMovies(final String movies_id) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DetailMovieItems> listItems = new ArrayList<>();
        final ArrayList<SearchItems> totalItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/movie/"+movies_id+"?api_key="+API_KEY+"&language="+languageID;
        final String url2 = "https://api.themoviedb.org/3/movie/"+movies_id+"/credits?api_key="+API_KEY;
        final String url3 = "https://api.themoviedb.org/3/movie/"+movies_id+"/videos?api_key="+API_KEY;
        //final String [] combineUrl = {url, url2, url3};
        //System.out.println(combineUrl.toString());

            client.get(url, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        final String result = new String(responseBody);

                        client.get(url2, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                try {
                                    final String result2 = new String(responseBody);

                                    client.get(url3, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            try {
                                                String result3 = new String(responseBody);
                                                JSONObject [] responseObject = new JSONObject[4];
                                                responseObject[0] = new JSONObject(result);
                                                responseObject[1] = new JSONObject(result2);
                                                responseObject[2] = new JSONObject(result3);
                                                responseObject[3] = responseObject[0].put("type_detail","moviedetail");

                                                //System.out.println("Result"+responseObject[0]);
                                                //System.out.println("Result"+responseObject[1]);
                                                //System.out.println("Result"+responseObject[2]);

                                                DetailMovieItems detailMovieItems = new DetailMovieItems(responseObject);
                                                listItems.add(detailMovieItems);
                                                listDetailMovies.postValue(listItems);


                                            } catch (Exception e) {
                                                Log.d("Exception", e.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            Log.d("onFailure", error.getMessage());
                                        }
                                    });


                                } catch (Exception e) {
                                    Log.d("Exception", e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.d("onFailure", error.getMessage());
                            }
                        });


                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure", error.getMessage());
                }
            });



    }


    void setDetailTV(final String tvies_id) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DetailMovieItems> listItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/tv/"+tvies_id+"?api_key="+API_KEY+"&language="+languageID;
        final String url2 = "https://api.themoviedb.org/3/tv/"+tvies_id+"/credits?api_key="+API_KEY;
        final String url3 = "https://api.themoviedb.org/3/tv/"+tvies_id+"/videos?api_key="+API_KEY;
        //final String [] combineUrl = {url, url2, url3};
        //System.out.println(combineUrl.toString());

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    final String result = new String(responseBody);

                    client.get(url2, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                final String result2 = new String(responseBody);

                                client.get(url3, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        try {
                                            String result3 = new String(responseBody);
                                            JSONObject [] responseObject = new JSONObject[4];
                                            responseObject[0] = new JSONObject(result);
                                            responseObject[1] = new JSONObject(result2);
                                            responseObject[2] = new JSONObject(result3);
                                            responseObject[3] = responseObject[0].put("type_detail","tvshowdetail");

                                            //System.out.println("Result"+responseObject[0]);
                                            //System.out.println("Result"+responseObject[1]);
                                            //System.out.println("Result"+responseObject[2]);

                                            DetailMovieItems detailMovieItems = new DetailMovieItems(responseObject);
                                            listItems.add(detailMovieItems);
                                            listDetailTvies.postValue(listItems);


                                        } catch (Exception e) {
                                            Log.d("Exception", e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Log.d("onFailure", error.getMessage());
                                    }
                                });


                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("onFailure", error.getMessage());
                        }
                    });


                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });



    }


    public void setSearch(final String searchQuery, final String searchCategory) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<SearchItems> listSearchItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language="+languageID+"&query="+searchQuery+"&page=1&include_adult=false";
        String url2 = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language="+languageID+"&query="+searchQuery+"&page=1&include_adult=false";

        switch (searchCategory){
            case "movie":
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String result = new String(responseBody);
                            JSONObject responseObject = new JSONObject(result);
                            JSONArray list = responseObject.getJSONArray("results");
                            String totalResult = String.valueOf(responseObject.getString("total_results"));
                            System.out.println("Hasil Result" + totalResult);

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                SearchItems searchItems = new SearchItems(movie, searchCategory);
                                listSearchItems.add(searchItems);
                            }
                            listsSearchItems.postValue(listSearchItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("onFailure", error.getMessage());
                    }
                });

                break;

            case "tvshow":
                client.get(url2, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String result = new String(responseBody);
                            JSONObject responseObject = new JSONObject(result);
                            JSONArray list = responseObject.getJSONArray("results");
                            String totalResult = String.valueOf(responseObject.getString("total_results"));
                            System.out.println("Hasil Result" + totalResult);
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                SearchItems searchItems = new SearchItems(movie, searchCategory);
                                listSearchItems.add(searchItems);
                            }
                            listsSearchItems.postValue(listSearchItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("onFailure", error.getMessage());
                    }
                });
                break;
        }


    }

    public void setTotalSearchResult(final String searchQuery) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<SearchItems> listSearchItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage().equals("in")){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language="+languageID+"&query="+searchQuery+"&page=1&include_adult=false";
        final String url2 = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language="+languageID+"&query="+searchQuery+"&page=1&include_adult=false";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    final String totalResult = String.valueOf(responseObject.getString("total_results"));
                    //System.out.println("Hasil Result" + totalResult);

                    client.get(url2, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody2) {
                            try {
                                String result2 = new String(responseBody2);
                                JSONObject responseObject2 = new JSONObject(result2);
                                String totalResult2 = String.valueOf(responseObject2.getString("total_results"));
                                //System.out.println("Hasil Result" + totalResult2);

                                ArrayList<String> mylist = new ArrayList<String>();
                                mylist.add(totalResult);
                                mylist.add(totalResult2);

                                listTotalResult.postValue(mylist);
                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("onFailure", error.getMessage());
                        }
                    });

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });


    }



    LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }
    LiveData<ArrayList<TvItems>> getTvs() {
        return listTv;
    }
    LiveData<ArrayList<DetailMovieItems>> getDetailMovies() {
        return listDetailMovies;
    }
    LiveData<ArrayList<DetailMovieItems>> getDetailTvies() {
        return listDetailTvies;
    }
    public LiveData<ArrayList<SearchItems>> getSearchItems() {
        return listsSearchItems;
    }

    public LiveData<ArrayList<String>> getTotalSearchResult(){
        return listTotalResult;
    }

}
