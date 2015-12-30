package com.example.my_movie.pick_a_movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent i = getActivity().getIntent();
            MovieModel movie =  i.getParcelableExtra("Movie Selection");

            TextView textView_title= (TextView)rootView.findViewById(R.id.textView_title);
            TextView textView_overview = (TextView)rootView.findViewById(R.id.textView_overview);
            TextView textView_rating = (TextView)rootView.findViewById(R.id.textView_rating);
            TextView textView_date = (TextView)rootView.findViewById(R.id.textView_date);
            ImageView imageView_thumbnail = (ImageView)rootView.findViewById(R.id.imageView_thumbnail);

            textView_date.setText(movie.release_date);
            textView_title.setText(movie.title);
            textView_rating.setText(movie.rating);
            textView_overview.setText(movie.overview);
            Picasso.with(getActivity().getApplicationContext()).load(movie.thumb_link).into(imageView_thumbnail);



            return rootView;
        }
    }
}
