package com.example.my_movie.pick_a_movie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by Aditi_Bhatnagar on 12/23/2015.
 */
public class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private List<MovieModel> movies;
    private final List<String> urls = new ArrayList<String>();
    //private final List<String> new_urls = new ArrayList<String>();


    public GridViewAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;

        for (MovieModel m : movies){
            urls.add(m.thumb_link);
        }
//        Collections.addAll(urls);
//
//        ArrayList<String> copy = new ArrayList<String>(urls);
//        urls.addAll(copy);

    }





    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
                        //.placeholder(R.drawable.placeholder) //
                        //.error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}


