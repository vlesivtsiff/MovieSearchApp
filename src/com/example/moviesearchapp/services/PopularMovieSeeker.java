package com.example.moviesearchapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.PopularMovie;

public class PopularMovieSeeker extends GenericSeeker<PopularMovie> {

	public static final String MOVIE_SEARCH_PATH = "/movie/popular";
	
	@Override
	public ArrayList<PopularMovie> find(String query) {
		ArrayList<PopularMovie> moviesList = retrieveMoviesList(query);
		return moviesList;
	}

	private ArrayList<PopularMovie> retrieveMoviesList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		return jsonParser.parsePopularMoviesResponse(response);
	}

	@Override
	public ArrayList<PopularMovie> find(String query, int maxResults) {
		ArrayList<PopularMovie> moviesList = retrieveMoviesList(query);
		return retrieveFirstResults(moviesList, maxResults);
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}

}
