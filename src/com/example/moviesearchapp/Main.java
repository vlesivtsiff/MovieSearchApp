package com.example.moviesearchapp;

import java.util.List;

import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.model.Person;
import com.example.moviesearchapp.model.PopularMovie;
import com.example.moviesearchapp.services.GenericSeeker;
import com.example.moviesearchapp.services.MovieSeeker;
import com.example.moviesearchapp.services.PersonSeeker;
import com.example.moviesearchapp.services.PopularMovieSeeker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface;
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

public class Main extends Activity {
	
	private static final String EMPTY_STRING = "";
	
	private EditText searchEditText;
	private RadioButton popularMoviesSearchRadioButton;
	private RadioButton moviesSearchRadioButton;
	private RadioButton peopleSearchRadioButton;
	private RadioGroup searchRadioGroup;
	private TextView searchTypeTextView;
	private Button searchButton;
	
	private GenericSeeker<PopularMovie> popularMovieSeeker = new PopularMovieSeeker();
	private GenericSeeker<Movie> movieSeeker = new MovieSeeker();
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
        
    	progressDialog = ProgressDialog.show(Main.this,
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
	
	private class PerformPopularMovieSearchTask extends AsyncTask<String, Void, List<PopularMovie>> {

		@Override
		protected List<PopularMovie> doInBackground(String... arg0) {
			String query = arg0[0];
			return popularMovieSeeker.find(query);
		}
		
		@Override
		protected void onPostExecute(final List<PopularMovie> result) {
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
				}
			});
		}
	}
	
	private class PerformMovieSearchTask extends AsyncTask<String, Void, List<Movie>> {

		@Override
		protected List<Movie> doInBackground(String... arg0) {
			String query = arg0[0];
			return movieSeeker.find(query);
		}
		
		@Override
		protected void onPostExecute(final List<Movie> result) {
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
					}
				}
			});
		}
	}
	
	private class PerformPersonSearchTask extends AsyncTask<String, Void, List<Person>> {
		
		@Override
		protected List<Person> doInBackground(String... params) {
			String query = params[0];
			return personSeeker.find(query);
		}
		
		@Override
		protected void onPostExecute(final List<Person> result) {			
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
					}
		    	}
		    });
		}
	}
}
