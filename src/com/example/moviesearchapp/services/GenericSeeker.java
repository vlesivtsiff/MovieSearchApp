package com.example.moviesearchapp.services;

import java.net.URLEncoder;
import java.util.ArrayList;

public abstract class GenericSeeker<E> {
/*
 * Example request
 * https://api.themoviedb.org/3/movie/550/images?api_key=f243d07dcfe9da6756d5df37fa41989c&language=en&include_image_language=en,null
 * */	
	protected static final String BASE_URL = "http://api.themoviedb.org/2.1";
//	protected static final String BASE_URL = "http://api.themoviedb.org/3";
	protected static final String LANGUAGE_PATH = "en/";
	protected static final String XML_FORMAT = "xml/";
	protected static final String API_KEY = "f243d07dcfe9da6756d5df37fa41989c";
	protected static final String SLASH = "/";
	
	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected XMLParser xmlParser = new XMLParser();
	
	public abstract ArrayList<E> find(String query);
	public abstract ArrayList<E> find(String query, int maxResults);
	
	public abstract String retrieveSearchMethodPath();

	protected String constructSearchUrl(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append(BASE_URL);
		sb.append(retrieveSearchMethodPath());
		sb.append(LANGUAGE_PATH);
		sb.append(XML_FORMAT);
		sb.append(API_KEY);
		sb.append(SLASH);
		sb.append(URLEncoder.encode(query));
		return sb.toString();
	}
	
	public ArrayList<E> retrieveFirstResults(ArrayList<E> list, int maxResults) {
		ArrayList<E> newList = new ArrayList<E>();
		int count = Math.min(list.size(), maxResults);
		for (int index = 0; index < count; index++) {
			newList.add(list.get(index));
		}
		return newList;
	}
}