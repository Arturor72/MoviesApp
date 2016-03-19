package com.styloop.moviesapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.styloop.moviesapp.adapter.MoviesAdapter;
import com.styloop.moviesapp.background.FetchMoviesTask;
import com.styloop.moviesapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG=MainActivityFragment.class.getSimpleName();
    private MoviesAdapter adapter=null;
    private List<Movie> movies=null;
    public MainActivityFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        List<Movie> myMovies=new ArrayList<>();
        Movie testMovie=new Movie();
        adapter =new MoviesAdapter(getActivity(), myMovies);
        GridView gridView=(GridView) rootView.findViewById(R.id.init_movies);
        gridView.setAdapter(adapter);
        return rootView;
    }
    public void updateMovies(){
        Log.v(LOG_TAG,"UPDATE");
        FetchMoviesTask fetchMovies=new FetchMoviesTask(getContext(),adapter);
        String[] string={"Popular"};
        fetchMovies.execute(string);

    }

}
