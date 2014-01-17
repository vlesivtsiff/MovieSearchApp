package com.example.moviesearchapp.model;

import java.util.ArrayList;

public class Movie {
	public String score;
	public String popularity;
	public boolean translated;
	public boolean adult;
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
	public String lastModifiedAt;
	public ArrayList<Image> imageList;
	
	public String retrieveThumbnail() {
		if(imageList != null && !imageList.isEmpty()) {
			for(Image movieImage : imageList) {
				if(movieImage.size.equalsIgnoreCase(Image.SIZE_THUMB) &&
						movieImage.type.equalsIgnoreCase(Image.TYPE_POSTER)) {
					return movieImage.url;
				}
			}
		}
		return null;
	}
}
