package com.example.moviesearchapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean adult;
	public String backdrop_path;
	public ArrayList<String> belongs_to_collection;
	public String budget;
	public ArrayList<Genres> genres;
	
	public String score;
	public String popularity;
	public boolean translated;
	
	public String language;
	public String originalName;
	public String name;
	public String type;
	public String id;
	public String imdbId;
	public String url;
	public String votes;
	public String rating;
	public String certification;
	public String overview;
	public String released;
	public String version;
	public String lastModifiedAt;
	public ArrayList<Image> imagesList;
		
	public String retrieveThumbnail() {
		if(imagesList != null && !imagesList.isEmpty()) {
			for(Image movieImage : imagesList) {
				if(movieImage.size.equalsIgnoreCase(Image.SIZE_THUMB) &&
						movieImage.type.equalsIgnoreCase(Image.TYPE_POSTER)) {
					return movieImage.url;
				}
			}
		}
		return null;
	}
	
	public class Genres {
		public String id;
		public String name;
	}
}
