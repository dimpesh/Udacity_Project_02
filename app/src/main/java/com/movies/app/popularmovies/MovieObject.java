package com.movies.app.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DIMPESH : ${month}
 */
// cp
// public class MovieObject implements Serializable{
public class MovieObject implements Parcelable{
    String title;
    String overview;
    String release_date;
    String vote_average;
    String poster_path;
    String backdrop_path;
    String id;

    public MovieObject(){}
    public MovieObject(String poster_path)
    {
        this.poster_path=poster_path;
    }

    protected MovieObject(Parcel in) {
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        id = in.readString();
    }

    @Override
    public String toString() {
        return title+overview;
    }

    public static final Creator<MovieObject> CREATOR = new Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote_average);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(id);
    }
}
