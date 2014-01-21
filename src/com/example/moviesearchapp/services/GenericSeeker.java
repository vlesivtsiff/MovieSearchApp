package com.example.moviesearchapp.services;

import java.net.URLEncoder;
import java.util.ArrayList;

public abstract class GenericSeeker<E> {
/*
 * Example request
 * https://api.themoviedb.org/3/movie/popular?api_key=f243d07dcfe9da6756d5df37fa41989c
 * https://api.themoviedb.org/3/movie/87421?api_key=f243d07dcfe9da6756d5df37fa41989c
 * */	
	
	protected static final String BASE_URL = "https://api.themoviedb.org/3";
	protected static final String API_KEY = "?api_key=f243d07dcfe9da6756d5df37fa41989c";
	protected static final String QUERY = "&query=";
	
	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected JSONParser jsonParser = new JSONParser();
	
	public abstract ArrayList<E> find(String query);
	public abstract ArrayList<E> find(String query, int maxResults);
	
	public abstract String retrieveSearchMethodPath();

	protected String constructSearchUrl(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append(BASE_URL);
		sb.append(retrieveSearchMethodPath());
		sb.append(API_KEY);
		sb.append(QUERY);
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
