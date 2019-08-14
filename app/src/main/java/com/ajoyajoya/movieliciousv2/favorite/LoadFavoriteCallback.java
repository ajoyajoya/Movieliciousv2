package com.ajoyajoya.movieliciousv2.favorite;

import java.util.ArrayList;

interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<FavoriteMovie> favoriteMovies);
}
