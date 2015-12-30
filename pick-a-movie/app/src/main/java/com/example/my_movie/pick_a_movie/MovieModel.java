package com.example.my_movie.pick_a_movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aditi_Bhatnagar on 12/29/2015.
 */
public class MovieModel implements Parcelable {

    String thumb_link;
    String title;
    String overview;
    String rating;
    String release_date;


    protected MovieModel(Parcel in) {
        thumb_link = in.readString();
        title = in.readString();
        overview = in.readString();
        rating = in.readString();
        release_date = in.readString();
    }

    public MovieModel() {
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumb_link);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(rating);
        parcel.writeString(release_date);
    }
}
