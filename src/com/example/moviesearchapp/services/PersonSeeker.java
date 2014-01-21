package com.example.moviesearchapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.Person;

public class PersonSeeker extends GenericSeeker<Person> {

	public static final String PERSON_SEARCH_PATH = "/search/person";
	
	@Override
	public ArrayList<Person> find(String query) {
		ArrayList<Person> moviesList = retrievePersonsList(query);
		return moviesList;
	}

	private ArrayList<Person> retrievePersonsList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		return jsonParser.parsePersonsResponse(response);
	}

	@Override
	public ArrayList<Person> find(String query, int maxResults) {
		ArrayList<Person> moviesList = retrievePersonsList(query);
		return retrieveFirstResults(moviesList, maxResults);
	}

	@Override
	public String retrieveSearchMethodPath() {
		return PERSON_SEARCH_PATH;
	}

}
