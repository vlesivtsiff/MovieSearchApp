package com.example.moviesearchapp;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviesearchapp.model.SearchMovie;
import com.example.moviesearchapp.ui.MoviesAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PopularMovieListActivity extends ListActivity {
	
	private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
	
	private ArrayList<SearchMovie> popularMoviesList = new ArrayList<SearchMovie>();
	private MoviesAdapter popularMoviesAdapter;
	
	DisplayImageOptions options;
	
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_layout);

        popularMoviesAdapter = new MoviesAdapter(this, R.layout.movie_data_row, popularMoviesList);
        popularMoviesList = (ArrayList<SearchMovie>) getIntent().getSerializableExtra("popularMovies");
        
        setListAdapter(popularMoviesAdapter);
        
        options = new DisplayImageOptions.Builder().
        		showImageOnLoading(R.drawable.ic_stub).
        		showImageForEmptyUri(R.drawable.ic_empty).
        		showImageOnFail(R.drawable.ic_error).
        		cacheInMemory(true).
        		cacheOnDisc(true).
        		considerExifParams(true).
        		displayer(new RoundedBitmapDisplayer(20)).
        		build();
        
        if (popularMoviesList!=null && !popularMoviesList.isEmpty()) {
            
        	popularMoviesAdapter.notifyDataSetChanged();
        	popularMoviesAdapter.clear();
        	int size = popularMoviesList.size();// > 5 ? 5 : popularMoviesList.size(); 
            for (int i = 0; i < size; i++) {
            	popularMoviesAdapter.add(popularMoviesList.get(i));
            }
        }
        
        popularMoviesAdapter.notifyDataSetChanged();
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
//		SearchMovie popularMovie = popularMoviesAdapter.getItem(position);
		
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
