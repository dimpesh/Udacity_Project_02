package com.movies.app.popularmovies.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DIMPESH : ${month}
 */
public class MovieContract
{
    public static final String CONTENT_AUTHORITY="com.movies.app.popularmovies.app";
    //Add the BASE_CONTENT_URI

    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

    //Path For Movies, Reviews and Trailers...
    public static final String PATH_MOVIE="movie";
//    public static final String PATH_TRAILER="trailer";
//    public static final String PATH_REVIEW="review";



    public static final class MovieEntry implements BaseColumns
    {
        //Table
        //    trying something  public static final String TABLE_MOVIE="movie_detail";
        public static final String TABLE_MOVIE="movie";



        // Columns name
        public static final String COLUMN_ID="_id";
        public static final String COLUMN_TITLE= "title";
        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_RELEASE_DATE="release_date";
        public static final String COLUMN_VOTE_AVERAGE="vote_average";
        public static final String COLUMN_POSTER_PATH="poster_path";
        public static final String COLUMN_BACKDROP_PATH="backdrop_path";
        public static final String COLUMN_MOVIE_ID="movie_id";


///*        CHECKING SOMETHING SO UNCOMMENT IF NOT RUNNING...
//        // Create Content Uri
//        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIE).build();
//
//        //Create cursor of base dir type for multiple entries
//        public static final String CONTENT_DIR_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_MOVIE;
//
//        // CURSOR FOR SINGLE ENTRY
//        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_MOVIE;
//
//        // FOR BUILDING URI ON INSERTION
//
//*/
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIE).build();
        public static final String CONTENT_DIR_TYPE="vnd.android.cursor.dir/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/"+CONTENT_AUTHORITY+"/"+PATH_MOVIE;




        // May change as per need
        public static Uri buildMoviesUri(long id)
        {

//            return CONTENT_URI.buildUpon().appendPath(id).build();
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

//    public static final class ReviewEntry implements BaseColumns
//    {
//
//        //Table
//        public static final String TABLE_REVIEW="movie_review";
//
//        // Columns name
//        public static final String COLUMN_ID="_id";
//        public static final String COLUMN_MOVIEID= "movie_id";
//        public static final String COLUMN_CONTENT= "content";
//
//        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
//
//        //Create cursor of base dir type for multiple entries
//        public static final String CONTENT_DIR_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_REVIEW;
//
//        // CURSOR FOR SINGLE ENTRY
//        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_REVIEW;
//
//        // FOR BUILDING URI ON INSERTION
//        public static Uri buildReviewsUri(long id)
//        {
//            return ContentUris.withAppendedId(CONTENT_URI,id);
//        }
//    }

//    public static final class TrailerEntry implements BaseColumns
//    {
//
//        //Table
//        public static final String TABLE_TRAILER="movie_trailer";
//
//        // Columns name
//        public static final String COLUMN_ID="_id";
//        public static final String COLUMN_MOVIEID= "movie_id";
//        public static final String COLUMN_KEY="key";
//
//
//        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
//
//        //Create cursor of base dir type for multiple entries
//        public static final String CONTENT_DIR_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_TRAILER;
//
//        // CURSOR FOR SINGLE ENTRY
//        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_TRAILER;
//
//        // FOR BUILDING URI ON INSERTION
//        public static Uri buildTrailer7sUri(long id)
//        {
//            return ContentUris.withAppendedId(CONTENT_URI,id);
//        }
//    }



}
