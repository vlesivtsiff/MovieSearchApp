package com.example.moviesearchapp.model;

import java.io.Serializable;

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TAG_PERSON_ADULT = "adult";
	public static final String TAG_PERSON_ID = "id";
	public static final String TAG_PERSON_NAME = "name";
	public static final String TAG_PERSON_PROFILE_PATH = "profile_path";
	
	public boolean adult;
	public String id;
	public String name;
	public String profile_path;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person: ");
		builder.append(name);
		builder.append(".");
		return builder.toString();
	}
}
