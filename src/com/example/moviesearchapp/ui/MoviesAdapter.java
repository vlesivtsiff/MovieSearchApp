package com.example.moviesearchapp.ui;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.example.moviesearchapp.R;
import com.example.moviesearchapp.io.FlushedInputStream;
import com.example.moviesearchapp.model.SearchMovie;
import com.example.moviesearchapp.services.HttpRetriever;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesAdapter extends ArrayAdapter<SearchMovie> {
    
    private HttpRetriever httpRetriever = new HttpRetriever();
    
    private ArrayList<SearchMovie> movieDataItems;
    
    private Activity context;
    
    public MoviesAdapter(Activity context, int textViewResourceId, ArrayList<SearchMovie> movieDataItems) {
        super(context, textViewResourceId, movieDataItems);
        this.context = context;
        this.movieDataItems = movieDataItems;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
  
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.movie_data_row, null);
        }
        
        SearchMovie searchMovie = movieDataItems.get(position);
        
        if (searchMovie != null) {
            
            // name
            TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            nameTextView.setText(searchMovie.title);
            
            // rating
            TextView ratingTextView = (TextView) view.findViewById(R.id.rating_text_view);
            ratingTextView.setText("Average vote: " + searchMovie.vote_average);
            
            // released
            TextView releasedTextView = (TextView) view.findViewById(R.id.released_text_view);
            releasedTextView.setText("Release Date: " + searchMovie.release_date);
            
            // certification
            TextView certificationTextView = (TextView) view.findViewById(R.id.certification_text_view);
            certificationTextView.setText("Certification: " + "TODO");
            
            // language
            TextView languageTextView = (TextView) view.findViewById(R.id.language_text_view);
            languageTextView.setText("Language: " + "TODO");
            
            // thumb image
            ImageView imageView = (ImageView) view.findViewById(R.id.movie_thumb_icon);
            String url = searchMovie.retrieveThumbnail();
            
            if (url != null) {
               	ImageLoader.getInstance().displayImage(url, imageView);
            }
            else {
                imageView.setImageBitmap(null);
            }
            
        }
        
        return view;
        
    }
    
    private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();
    
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }
    
    private Bitmap fetchBitmapFromCache(String url) {
        
        synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        return null;
        
    }
    
    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            InputStream is = httpRetriever.retrieveStream(url);
            if (is==null) {
                  return null;
            }
            return BitmapFactory.decodeStream(new FlushedInputStream(is));
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {            
            if (isCancelled()) {
                bitmap = null;
            }
            
            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    
}