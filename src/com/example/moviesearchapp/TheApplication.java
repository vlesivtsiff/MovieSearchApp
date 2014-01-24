package com.example.moviesearchapp;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class TheApplication extends Application {
	
	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
    		getApplicationContext()
    		).build();
    ImageLoader.getInstance().init(config);

}
