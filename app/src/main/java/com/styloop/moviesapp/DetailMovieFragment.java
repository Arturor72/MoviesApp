package com.styloop.moviesapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.styloop.moviesapp.common.Constants;
import com.styloop.moviesapp.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieFragment extends Fragment {

    Movie movie;
    public DetailMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_detail_movie, container, false);
        Intent intent=getActivity().getIntent();
        if(intent!=null&&intent.hasExtra(Constants.EXTRA_KEY_MOVIE)){
            movie=(Movie)intent.getExtras().get(Constants.EXTRA_KEY_MOVIE);
            ((TextView)rootView.findViewById(R.id.movieTitle)).setText(movie.getOriginalTitle());
            Picasso.with(getActivity()).load(createURLImage(movie)).resize(370,556).into(((ImageView) rootView.findViewById(R.id.moviePoster)));
            ((TextView) rootView.findViewById(R.id.movieRating)).setText("Rating: "+ movie.getRating());
            ((TextView)rootView.findViewById(R.id.movieReleaseDate)).setText(movie.getYearDate());
            ((TextView)rootView.findViewById(R.id.movieSynopsis)).setText(movie.getSynopsis());
        }
        return rootView;
    }
    private String createURLImage(Movie movie){
       /*http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg */
        String url="";
        final String MOVIEDB_BASE_URL="http://image.tmdb.org/t/p/w185/";
        url=MOVIEDB_BASE_URL+movie.getUrlPoster();
        return url;
    }
}
