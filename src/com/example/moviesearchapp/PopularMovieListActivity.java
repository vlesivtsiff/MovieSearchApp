package com.example.moviesearchapp;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviesearchapp.model.SearchMovie;

public class PopularMovieListActivity extends ListActivity {
	
	private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
	
	private ArrayList<SearchMovie> popularMoviesList;
	private ArrayAdapter<SearchMovie> popularMoviesAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_layout);
        
        popularMoviesList = (ArrayList<SearchMovie>) getIntent().getSerializableExtra("popularMovies");
        popularMoviesAdapter = new ArrayAdapter<SearchMovie>(this, android.R.layout.simple_list_item_1, popularMoviesList);
        
        setListAdapter(popularMoviesAdapter);
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		SearchMovie popularMovie = popularMoviesAdapter.getItem(position);
		
		String imdbId = "tt1411250";//popularMovie.id;
		if (imdbId == null || imdbId.length() == 0) {
			longToast(getString(R.string.no_imdb_id_found));
			return;
		}
		
		String imdbUrl = IMDB_BASE_URL + "tt1411250";//popularMovie.id;
		Intent imdbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));				
		startActivity(imdbIntent);
		
	}
	
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

}