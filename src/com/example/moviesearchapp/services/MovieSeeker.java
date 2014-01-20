package com.example.moviesearchapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.Movie;

public class MovieSeeker extends GenericSeeker<Movie> {

	public static final String MOVIE_SEARCH_PATH = "/movie";
	
	@Override
	public ArrayList<Movie> find(String query) {
		ArrayList<Movie> moviesList = retrieveMoviesList(query);
		return moviesList;
	}

	private ArrayList<Movie> retrieveMoviesList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		return null;
	}

	@Override
	public ArrayList<Movie> find(String query, int maxResults) {
		ArrayList<Movie> moviesList = retrieveMoviesList(query);
		return retrieveFirstResults(moviesList, maxResults);
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}

}
