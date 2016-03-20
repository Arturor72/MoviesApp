package com.styloop.moviesapp.util;

import com.styloop.moviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arturo on 3/18/16.
 */
public class Util {

    public static Movie[] getMoviesJson(String moviesAsString) throws JSONException{
        final String JSON_MOVIE_RESULTS="results";
        Movie[] movies=null;
        JSONObject moviesObjectJson=new JSONObject(moviesAsString);
        JSONArray resultsArray=moviesObjectJson.getJSONArray(JSON_MOVIE_RESULTS);
        if(moviesObjectJson!=null && resultsArray!=null){
            movies=new Movie[resultsArray.length()];
        }
        addMovies(movies,resultsArray);
        return movies;
    }
    public static void addMovies(Movie[] movies, JSONArray resultsArray) throws  JSONException{
        final String JSON_MOVIE_ID="id";
        final String JSON_MOVIE_TITLE="title";
        final String JSON_MOVIE_POSTER_URL="poster_path";
        final String JSON_MOVIE_SYNOPSIS="overview";
        final String JSON_MOVIE_RELEASE_DATE="release_date";
        final String JSON_MOVIE_RATING="vote_average";
        for (int i=0;i<resultsArray.length();i++){
            Movie movie=new Movie();
            JSONObject resultObject=resultsArray.getJSONObject(i);
            movie.setId(resultObject.getString(JSON_MOVIE_ID));
            movie.setOriginalTitle(resultObject.getString(JSON_MOVIE_TITLE));
            movie.setSynopsis(resultObject.getString(JSON_MOVIE_SYNOPSIS));
            movie.setRating(resultObject.getString(JSON_MOVIE_RATING));
            movie.setReleaseDate(resultObject.getString(JSON_MOVIE_RELEASE_DATE));
            movie.setUrlPoster(resultsArray.getJSONObject(i).getString(JSON_MOVIE_POSTER_URL));
            movies[i]=movie;
        }
    }
}
