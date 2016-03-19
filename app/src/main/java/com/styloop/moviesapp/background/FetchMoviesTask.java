package com.styloop.moviesapp.background;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.styloop.moviesapp.R;
import com.styloop.moviesapp.adapter.MoviesAdapter;
import com.styloop.moviesapp.model.Movie;
import com.styloop.moviesapp.util.Util;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by arturo on 3/18/16.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {
    private final static String LOG_TAG=FetchMoviesTask.class.getSimpleName();
    Context context;
    MoviesAdapter adapter;
    public FetchMoviesTask(Context context, MoviesAdapter adapter) {
        this.context=context;
        this.adapter=adapter;
    }

    @Override
    protected Movie[] doInBackground(String ... params) {
        Log.d(LOG_TAG,"+ doInBackground");
        Log.d(LOG_TAG,"ORDER BY: "+params[0]);
        String orderBy=params[0];

        Movie[] movies=null;
        try {
            movies=getMovies(orderBy);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG,"- doInBackground");
        return movies;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {

        if(movies!=null){
            for (Movie movie:
                 movies) {
                Log.v(LOG_TAG,"Movie: "+movie.getOriginalTitle());
            }
            adapter.clear();
            adapter.addAll(Arrays.asList(movies));
        }

    }

    public Movie[] getMovies(String orderBy) throws  MalformedURLException, IOException, JSONException{
        Log.d(LOG_TAG,"+ getMovies");
        String dataMovies=getData(orderBy);
        Movie[] movies=Util.getMoviesJson(dataMovies);
        Log.d(LOG_TAG,"- getMovies");
        return movies;
    }
    public String getData(String orderBy) throws  MalformedURLException, IOException{
        Log.d(LOG_TAG,"+ getData");
        StringBuffer stringBuffer=new StringBuffer();
        HttpURLConnection connection=getHttpConnection(orderBy);
        InputStream inputStream=connection.getInputStream();
        if(inputStream==null){
            return null;
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        while((line=reader.readLine())!=null){
            stringBuffer.append(line+"\n");
        }
        if (stringBuffer.length()==0){
            return null;
        }
        connection.disconnect();
        if (reader!=null){
            reader.close();
        }
        Log.d(LOG_TAG,"- getData");
        return stringBuffer.toString();
    }
    public HttpURLConnection getHttpConnection(String orderBy) throws MalformedURLException, IOException{
        HttpURLConnection connection=null;
        URL urlMovieApi=new URL(buildURL(orderBy));
        connection=(HttpURLConnection)urlMovieApi.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        return connection;
    }

    public String buildURL(String orderBy){
        final String MOVIEDB_BASE_URL="http://api.themoviedb.org/3";
        final String MOVIE_POPULAR="movie/popular";
        final String MOVIE_TOP_RATED="movie/top_rated";
        final String APP_ID="api_key";
        String urlAsString="";
        String BASE_URL_FINAL=MOVIEDB_BASE_URL;

        if (context.getString(R.string.order_by_popular).equals(orderBy)) {
            BASE_URL_FINAL=MOVIEDB_BASE_URL+ File.separator+MOVIE_POPULAR;
        }else if(context.getString(R.string.order_by_top).equals(orderBy)){
            BASE_URL_FINAL=MOVIEDB_BASE_URL+ File.separator+MOVIE_TOP_RATED;
        }
        Uri buildUri=Uri.parse(BASE_URL_FINAL).buildUpon().appendQueryParameter(APP_ID,"xxxxxxx").build();
        urlAsString=buildUri.toString();
        Log.d(LOG_TAG,"URL API MOVIE: "+urlAsString);
        return urlAsString;
    }

}
