package com.example.moviesearchapp.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.model.Person;
import com.example.moviesearchapp.model.PopularMovie;

public class JSONParser {
	
	private static final String TAG_RESULTS = "results";
	

	public ArrayList<Person> parsePersonsResponse(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PopularMovie> parsePopularMoviesResponse(String jsonString) {
		ArrayList<PopularMovie> movieList = new ArrayList<PopularMovie>();
		try {
			JSONArray results = null;
			JSONObject jsonObj = new JSONObject(jsonString);
			results = jsonObj.getJSONArray(TAG_RESULTS);
			for(int index = 0; index < results.length(); index++) {
				
				JSONObject res = results.getJSONObject(index);
				
				PopularMovie pm = new PopularMovie();
				pm.adult = res.getBoolean(PopularMovie.TAG_MOVIE_ADULT);
				pm.backdrop_path = res.getString(PopularMovie.TAG_MOVIE_BACKDROP_PATH);
				pm.id = res.getString(PopularMovie.TAG_MOVIE_ID);
				pm.original_title = res.getString(PopularMovie.TAG_MOVIE_ORIGINAL_TITLE);
				pm.popularity = res.getString(PopularMovie.TAG_MOVIE_POPULARITY);
				pm.poster_path = res.getString(PopularMovie.TAG_MOVIE_POSTER_PATH);
				pm.release_date = res.getString(PopularMovie.TAG_MOVIE_RELEASE_DATE);
				pm.title = res.getString(PopularMovie.TAG_MOVIE_TITLE);
				pm.vote_average = res.getString(PopularMovie.TAG_MOVIE_VOTE_AVERAGE);
				pm.vote_count = res.getString(PopularMovie.TAG_MOVIE_VOTE_COUNT);
				
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
