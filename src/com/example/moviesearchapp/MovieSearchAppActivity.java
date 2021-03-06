package com.example.moviesearchapp;

import java.util.ArrayList;

import com.example.moviesearchapp.TheApplication;

import com.example.moviesearchapp.model.Person;
import com.example.moviesearchapp.model.SearchMovie;
import com.example.moviesearchapp.services.GenericSeeker;
import com.example.moviesearchapp.services.SearchMovieSeeker;
import com.example.moviesearchapp.services.PersonSeeker;
import com.example.moviesearchapp.services.PopularMovieSeeker;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MovieSearchAppActivity extends Activity {
	
	private static final String EMPTY_STRING = "";
	
	private EditText searchEditText;
	private RadioButton popularMoviesSearchRadioButton;
	private RadioButton moviesSearchRadioButton;
	private RadioButton peopleSearchRadioButton;
	private RadioGroup searchRadioGroup;
	private TextView searchTypeTextView;
	private Button searchButton;
	
	private GenericSeeker<SearchMovie> searchMovieSeeker = new SearchMovieSeeker();
	private GenericSeeker<SearchMovie> popularMovieSeeker = new PopularMovieSeeker();	
	private GenericSeeker<Person> personSeeker = new PersonSeeker();
	
	private ProgressDialog progressDialog;

	private void findAllViewsById()
	{
		searchEditText = (EditText) findViewById(R.id.search_edit_text);
		popularMoviesSearchRadioButton = (RadioButton) findViewById(R.id.popular_movie_search_radio_button);
		moviesSearchRadioButton = (RadioButton) findViewById(R.id.movie_search_radio_button);
		peopleSearchRadioButton = (RadioButton) findViewById(R.id.people_search_radio_button);
		searchRadioGroup = (RadioGroup)findViewById(R.id.search_radio_group);
		searchTypeTextView = (TextView)findViewById(R.id.search_type_text_view);
		searchButton = (Button)findViewById(R.id.search_button);
	}

	private OnClickListener radioButtonListener = new OnClickListener() {
		public void onClick(View v) {
			RadioButton radioButton = (RadioButton) v;
			searchTypeTextView.setText(radioButton.getText());
		}
	};
	
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.findAllViewsById();
		
		popularMoviesSearchRadioButton.setOnClickListener(radioButtonListener);
		moviesSearchRadioButton.setOnClickListener(radioButtonListener);
		peopleSearchRadioButton.setOnClickListener(radioButtonListener);
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				String query = searchEditText.getText().toString();
				performSearch(query);				

				if(popularMoviesSearchRadioButton.isChecked()){
					longToast(popularMoviesSearchRadioButton.getText() + " " + query);
				}
				else if(moviesSearchRadioButton.isChecked()){
					longToast(moviesSearchRadioButton.getText() + " " + query);
				}
				else if(peopleSearchRadioButton.isChecked()){
					longToast(peopleSearchRadioButton.getText() + " " + query);
				}
			}
		});
		
		searchEditText.setOnFocusChangeListener(
				new DefaultTextOnFocusListener(getString(R.string.search)));
		int id = searchRadioGroup.getCheckedRadioButtonId();
		if(-1 != id)
		{
			RadioButton radioButton = (RadioButton) findViewById(id);
			searchTypeTextView.setText(radioButton.getText());
		}
	}
	
	private class DefaultTextOnFocusListener implements OnFocusChangeListener {
		
		private String defaultText;
		
		public DefaultTextOnFocusListener(String defaultText) {
			this.defaultText = defaultText;
		}
		
		public void onFocusChange(View v, boolean hasFocus) {
			if(v instanceof EditText) {
				EditText focusedEditText = (EditText) v;
				// handle obtaining focus
				if(hasFocus) {
					if(focusedEditText.getText().toString().equals(defaultText)) {
						focusedEditText.setText(EMPTY_STRING);
					}
				}
				// handle loosing focus
				else {
					if(focusedEditText.getText().toString().equals(EMPTY_STRING)) {
						focusedEditText.setText(defaultText);
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void performSearch(String query) {
        
    	progressDialog = ProgressDialog.show(MovieSearchAppActivity.this,
        		"Please wait...", "Retrieving data...", true, true);
    	
        if (popularMoviesSearchRadioButton.isChecked()) {
        	PerformPopularMovieSearchTask task = new PerformPopularMovieSearchTask();
        	task.execute(query);
        	progressDialog.setOnCancelListener(new CancelTaskOnCancelListener(task));
		}
        if (moviesSearchRadioButton.isChecked()) {
        	PerformMovieSearchTask task = new PerformMovieSearchTask();
        	task.execute(query);
        	progressDialog.setOnCancelListener(new CancelTaskOnCancelListener(task));
		}
		else if (peopleSearchRadioButton.isChecked()) {
			PerformPersonSearchTask task = new PerformPersonSearchTask();
        	task.execute(query);
        	progressDialog.setOnCancelListener(new CancelTaskOnCancelListener(task));
		}   
    }
	
	private class CancelTaskOnCancelListener implements OnCancelListener {
    	private AsyncTask<?, ?, ?> task;
    	public CancelTaskOnCancelListener(AsyncTask<?, ?, ?> task) {
    		this.task = task;
    	}
    	public void onCancel(DialogInterface dialog) {
    		if (task!=null) {
        		task.cancel(true);
        	}
		}
    }
	
	private class PerformPopularMovieSearchTask extends AsyncTask<String, Void, ArrayList<SearchMovie>> {

		@Override
		protected ArrayList<SearchMovie> doInBackground(String... arg0) {
			String query = "";
			return popularMovieSeeker.find(query);
		}
		
		@Override
		protected void onPostExecute(final ArrayList<SearchMovie> result) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if(progressDialog != null) {
						progressDialog.dismiss();
						progressDialog = null;
					}
					
					if(result!=null) {
						longToast("Got " + result.size() + " popular movies");

//						for(PopularMovie popularMovie : result) {
//							longToast(popularMovie.title + " - " + popularMovie.vote_average);
//						}
					}
					
					Intent intent = new Intent(MovieSearchAppActivity.this, PopularMovieListActivity.class);
					intent.putExtra("popularMovies", result);
					startActivity(intent);

				}
			});
		}
	}
	
	private class PerformMovieSearchTask extends AsyncTask<String, Void, ArrayList<SearchMovie>> {

		@Override
		protected ArrayList<SearchMovie> doInBackground(String... arg0) {
			String query = arg0[0];
			if(query.length() > 0) {
				return searchMovieSeeker.find(query);
			}
			return null;	
		}
		
		@Override
		protected void onPostExecute(final ArrayList<SearchMovie> result) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if(progressDialog != null) {
						progressDialog.dismiss();
						progressDialog = null;
					}
					if(result!=null) {
						longToast("Got " + result.size() + " movies");
//						for(Movie _movie : result) {
//							longToast(_movie.name + " - " + _movie.rating);
//						}
						Intent intent = new Intent(MovieSearchAppActivity.this, SearchMovieListActivity.class);
						intent.putExtra("searchMovies", result);
						startActivity(intent);
					}
					else
					{
						longToast("Nothing to search!");
					}
				}
			});
		}
	}
	
	private class PerformPersonSearchTask extends AsyncTask<String, Void, ArrayList<Person>> {
		
		@Override
		protected ArrayList<Person> doInBackground(String... params) {
			String query = params[0];
			if(query.length() > 0) {
				return personSeeker.find(query);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<Person> result) {			
			runOnUiThread(new Runnable() {
		    	@Override
		    	public void run() {
		    		if (progressDialog!=null) {
		    			progressDialog.dismiss();
		    			progressDialog = null;
		    		}
		    		if (result != null) {
		    			longToast("Got " + result.size() + " persons");
//						for (Person person : result) {
//							longToast(person.name);
//						}
		    			Intent intent = new Intent(MovieSearchAppActivity.this, SearchPersonListActivity.class);
						intent.putExtra("searchPersons", result);
						startActivity(intent);
					}
		    		else
					{
						longToast("No Persons found!");
					}
		    	}
		    });
		}
	}
}
