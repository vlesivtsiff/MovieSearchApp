package com.example.moviesearchapp.model;

import java.io.Serializable;

public class SearchMovie implements Serializable {
	
	private static final long serialVersionUID = 0L;
	
	public static final String THUMB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92";
	
	public static final String TAG_MOVIE_ADULT = "adult";
	public static final String TAG_MOVIE_BACKDROP_PATH = "backdrop_path";
	public static final String TAG_MOVIE_ID = "id";
	public static final String TAG_MOVIE_ORIGINAL_TITLE = "original_title";
	public static final String TAG_MOVIE_RELEASE_DATE = "release_date";
	public static final String TAG_MOVIE_POSTER_PATH = "poster_path";
	public static final String TAG_MOVIE_POPULARITY = "popularity";
	public static final String TAG_MOVIE_TITLE = "title";
	public static final String TAG_MOVIE_VOTE_AVERAGE = "vote_average";
	public static final String TAG_MOVIE_VOTE_COUNT = "vote_count";
	
	public boolean adult;
	public String backdrop_path;
	public String id;
	public String original_title;
	public String release_date;
	public String poster_path;
	public String popularity;
	public String title;
	public String vote_average;
	public String vote_count;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie: ");
		builder.append(title);
		builder.append(".");
		return builder.toString();
	}

	public String retrieveThumbnail() {
		String res;
		if(poster_path.length() > 0) {
			res = THUMB_IMAGE_BASE_URL + poster_path;
		}
		else {
			res = "https://yt3.ggpht.com/-b2IKiX6WiV4/AAAAAAAAAAI/AAAAAAAAAAA/L_i4w667kIk/s48-c-k-no/photo.jpg";
		}
		return res;
	}
}
