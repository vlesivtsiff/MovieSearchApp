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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PopularMovieListActivity extends ListActivity {
	
	private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
	
	private ArrayList<SearchMovie> popularMoviesList = new ArrayList<SearchMovie>();
	private MoviesAdapter popularMoviesAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_layout);
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
        		getApplicationContext()
        		).build();
        ImageLoader.getInstance().init(config);

        popularMoviesAdapter = new MoviesAdapter(this, R.layout.movie_data_row, popularMoviesList);
        popularMoviesList = (ArrayList<SearchMovie>) getIntent().getSerializableExtra("popularMovies");
        
        setListAdapter(popularMoviesAdapter);
        
        if (popularMoviesList!=null && !popularMoviesList.isEmpty()) {
            
        	popularMoviesAdapter.notifyDataSetChanged();
        	popularMoviesAdapter.clear();
            for (int i = 0; i < popularMoviesList.size(); i++) {
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
