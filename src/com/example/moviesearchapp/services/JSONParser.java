package com.example.moviesearchapp.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.model.Person;
import com.example.moviesearchapp.model.SearchMovie;

public class JSONParser {
	
	private static final String TAG_RESULTS = "results";
	

	public ArrayList<Person> parsePersonsResponse(String jsonString) {
		ArrayList<Person> personsList = new ArrayList<Person>();
		try {
			JSONArray results = null;
			JSONObject jsonObj = new JSONObject(jsonString);
			results = jsonObj.getJSONArray(TAG_RESULTS);
			for(int index = 0; index < results.length(); index++) {
				
				JSONObject res = results.getJSONObject(index);
				
				Person p = new Person();
				p.adult = res.getBoolean(Person.TAG_PERSON_ADULT);
				p.id = res.getString(Person.TAG_PERSON_ID);
				p.name = res.getString(Person.TAG_PERSON_NAME);
				p.profile_path = res.getString(Person.TAG_PERSON_PROFILE_PATH);
				
				personsList.add(p);
			}			
		}
		catch (JSONException e)
		{
			;
		}		
		
		return personsList;
	}

	public ArrayList<SearchMovie> parseSearchMoviesResponse(String jsonString) {
		ArrayList<SearchMovie> movieList = new ArrayList<SearchMovie>();
		try {
			JSONArray results = null;
			JSONObject jsonObj = new JSONObject(jsonString);
			results = jsonObj.getJSONArray(TAG_RESULTS);
			for(int index = 0; index < results.length(); index++) {
				
				JSONObject res = results.getJSONObject(index);
				
				SearchMovie pm = new SearchMovie();
				pm.adult = res.getBoolean(SearchMovie.TAG_MOVIE_ADULT);
				pm.backdrop_path = res.getString(SearchMovie.TAG_MOVIE_BACKDROP_PATH);
				pm.id = res.getString(SearchMovie.TAG_MOVIE_ID);
				pm.original_title = res.getString(SearchMovie.TAG_MOVIE_ORIGINAL_TITLE);
				pm.popularity = res.getString(SearchMovie.TAG_MOVIE_POPULARITY);
				pm.poster_path = res.getString(SearchMovie.TAG_MOVIE_POSTER_PATH);
				pm.release_date = res.getString(SearchMovie.TAG_MOVIE_RELEASE_DATE);
				pm.title = res.getString(SearchMovie.TAG_MOVIE_TITLE);
				pm.vote_average = res.getString(SearchMovie.TAG_MOVIE_VOTE_AVERAGE);
				pm.vote_count = res.getString(SearchMovie.TAG_MOVIE_VOTE_COUNT);
				
				movieList.add(pm);
			}			
		}
		catch (JSONException e)
		{
			;
		}		
		
		return movieList;
	}

	public ArrayList<Movie> parseMoviesResponse(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

}
