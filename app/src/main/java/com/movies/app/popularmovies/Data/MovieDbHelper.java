package com.movies.app.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DIMPESH : ${month}
 */
public class MovieDbHelper extends SQLiteOpenHelper{
    public static final String LOG_TAG=MovieDbHelper.class.getSimpleName();

    // Database name and version
    public static final String DATABASE_NAME="movies.db";
    public static final int DATABASE_VERSION=4;



    public MovieDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE="CREATE TABLE "+ MovieContract.MovieEntry.TABLE_MOVIE+"("
                +MovieContract.MovieEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +MovieContract.MovieEntry.COLUMN_TITLE+" TEXT NOT NULL,"
                +MovieContract.MovieEntry.COLUMN_OVERVIEW+" TEXT NOT NULL,"
                +MovieContract.MovieEntry.COLUMN_RELEASE_DATE+" TEXT NOT NULL,"
                +MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE+" TEXT NOT NULL,"
                +MovieContract.MovieEntry.COLUMN_POSTER_PATH+" TEXT NOT NULL,"
                +MovieContract.MovieEntry.COLUMN_BACKDROP_PATH+" TEXT NOT NULL, "
                +MovieContract.MovieEntry.COLUMN_MOVIE_ID +" INTEGER UNIQUE );";

        // try declaring text
 //              +MovieContract.MovieEntry.COLUMN_POSTER_PATH+" BLOB NOT NULL,"
 //               +MovieContract.MovieEntry.COLUMN_BACKDROP_PATH+" BLOB NOT NULL);";

//        final String SQL_CREATE_REVIEW_TABLE="CREATE TABLE "+ MovieContract.ReviewEntry.TABLE_REVIEW+"("
//                + MovieContract.ReviewEntry.COLUMN_ID+" INTEGER NOT NULL ,"
//                +MovieContract.ReviewEntry.COLUMN_MOVIEID+" TEXT NOT NULL ,"
//                +MovieContract.ReviewEntry.COLUMN_CONTENT+" TEXT NOT NULL);";
//
//        final String SQL_CREATE_TRAILER_TABLE="CREATE TABLE "+ MovieContract.TrailerEntry.TABLE_TRAILER+"("
//                + MovieContract.TrailerEntry.COLUMN_ID+" INTEGER NOT NULL ,"
//                +MovieContract.TrailerEntry.COLUMN_MOVIEID+" TEXT NOT NULL ,"
//                +MovieContract.TrailerEntry.COLUMN_KEY+" TEXT NOT NULL);";
//

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
//        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
//        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);



    }


    //Update the Database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop the Table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_MOVIE);
        // Recreate the Table
        onCreate(sqLiteDatabase);
        // Drop the Table
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.ReviewEntry.TABLE_REVIEW);
        // Recreate the Table
//        onCreate(sqLiteDatabase);
        // Drop the Table
  //      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.TrailerEntry.TABLE_TRAILER);
        // Recreate the Table
  //      onCreate(sqLiteDatabase);

    }
}
