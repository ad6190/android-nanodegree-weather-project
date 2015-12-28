package com.example.my_movie.pick_a_movie;

/**
 * Created by Aditi_Bhatnagar on 12/23/2015.
 */

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridViewFragment extends Fragment {

    private GridViewAdapter gridViewAdapter;

    public GridViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

        //Loading image from below url into gridView
        Context c = getActivity().getApplicationContext();
        GridView gv = (GridView) rootView.findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(c);
        gv.setAdapter(gridViewAdapter);
//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
//                String forecast = gridViewAdapter.getItem(position);
//                Intent intent = new Intent(getActivity(), DetailActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT, forecast);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }


    private void updateMovie() {
        FetchMovieTask movieUrlTask = new FetchMovieTask();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String location = prefs.getString(getString(R.string.pref_location_key),
//                getString(R.string.pref_location_default));
        movieUrlTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, List> {
        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now.
         */

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private List getMovieDataFromJson(String discoverJsonStr)
                throws JSONException {

            final String OM_LIST = "results";
            final String OM_POSTER_PATH = "poster_path";
            final String OM_RELEASE_DATE = "release_date";
            final String OM_RATING = "vote_average";


            JSONObject discoverJson = new JSONObject(discoverJsonStr);
            JSONArray moviesArray = discoverJson.getJSONArray(OM_LIST);

            List<String> resultStrs = new ArrayList<String>();
            for(int i = 0; i < moviesArray.length(); i++) {

                JSONObject movieData = moviesArray.getJSONObject(i);
                String poster_path = movieData.getString(OM_POSTER_PATH);
                String release_date = movieData.getString(OM_RELEASE_DATE);
                String rating = movieData.getString(OM_RATING);
                resultStrs.add(poster_path) ;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Movie entry: " + s);
            }
            return resultStrs;

        }

        protected List doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;



            try {

                final String MOVIE_POSTER_BASE_URL = Data.BASE;
                final String APPID_PARAM = "api_key";
                final String SORT_BY = "sort_by";

                Uri builtUri = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_MAP_API_KEY)
                        .appendQueryParameter(SORT_BY, Data.SORT_BY) //get sort_by using intent from preference
                        .build();

                URL url = new URL(builtUri.toString());

                Log.i("Movie Configuration URL","Built URI " + builtUri.toString());



                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.d("Movie db response", movieJsonStr);
            } catch (IOException e) {
                Log.e("Movie db error", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("GridViewFragment", "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List results) {
//            if (results != null) {
//            if (results != null) {
//                gridViewAdapter.clear();
//                for(List eachMovie : results){
//                    gridViewAdapter.add(eachMovie);
//                }
//            }

            }
        }
}



