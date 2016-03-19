package com.styloop.moviesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.styloop.moviesapp.R;
import com.styloop.moviesapp.model.Movie;

import java.util.List;

/**
 * Created by arturo on 3/18/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {
    private final static String LOG_TAG=MoviesAdapter.class.getSimpleName();
    public MoviesAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie =getItem(position);
        ImageView imageView;
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item_movies,parent,false);
            imageView=(ImageView)convertView.findViewById(R.id.poster);
        }else{
            imageView=(ImageView)convertView.findViewById(R.id.poster);
        }

        Picasso.with(convertView.getContext()).load(createURLImage(movie)).resize(370,556).into(imageView);

        return convertView;
    }
    private String createURLImage(Movie movie){
       /*http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg */
       String url="";
       final String MOVIEDB_BASE_URL="http://image.tmdb.org/t/p/w185/";
       url=MOVIEDB_BASE_URL+movie.getUrlPoster();
       return url;
    }
}
