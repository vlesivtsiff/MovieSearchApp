package com.example.moviesearchapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.SearchMovie;

public class SearchMovieSeeker extends GenericSeeker<SearchMovie> {

	public static final String MOVIE_SEARCH_PATH = "/search/movie";
	
	@Override
	public ArrayList<SearchMovie> find(String query) {
		ArrayList<SearchMovie> moviesList = retrieveMoviesList(query);
		return moviesList;
	}

	private ArrayList<SearchMovie> retrieveMoviesList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		return jsonParser.parseSearchMoviesResponse(response);
	}

	@Override
	public ArrayList<SearchMovie> find(String query, int maxResults) {
		ArrayList<SearchMovie> moviesList = retrieveMoviesList(query);
		return retrieveFirstResults(moviesList, maxResults);
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}

}
