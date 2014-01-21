package com.example.moviesearchapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
	public String score;
	public String popularity;
	public String name;
	public String id;
	public String bibliography;
	public String url;
	public String version;
	public String lastModifiedAt;
	public ArrayList<Image> imagesList;
}
