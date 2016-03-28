package com.movies.app.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by DIMPESH : ${month}
 */
public class MovieProvider extends ContentProvider{

    public static final String LOG_TAG=MovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mDbHelper;

    // Codes For UriMatcher
// May Try to remove static
    private static final int MOVIE=100;
    // try to do also by removing static
    private  static final int MOVIE_WITH_ID=200;

//  For
    private static final int REVIEW=50;
    //code for ID Specific
    private static final int REVIEW_WITH_ID=60;




    // Uptil Correct...

    private static UriMatcher buildUriMatcher()
    {
        // Build URI Matcher By adding specific code to return based on common to use NO_MATCHER

        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);

        final String authority=MovieContract.CONTENT_AUTHORITY;
        // add code for each type we add the Add Uri matcher

        matcher.addURI(authority,MovieContract.PATH_MOVIE,MOVIE);
        matcher.addURI(authority,MovieContract.MovieEntry.TABLE_MOVIE+"/*",MOVIE_WITH_ID);


        return matcher;

    }



    @Override
    public boolean onCreate() {

        mDbHelper=new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
   //     Cursor retCursor;
        final int  match=sUriMatcher.match(uri);
        switch (match)
        {
            case MOVIE :
            {
                return MovieContract.MovieEntry.CONTENT_DIR_TYPE;
            }
            case MOVIE_WITH_ID :
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:throw new UnsupportedOperationException("Unsupported URI :"+uri);
        }

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[]selArgs, String sort) {
        Cursor retCursor = null;


        switch (sUriMatcher.match(uri))
        {
            case MOVIE : {
                retCursor = mDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_MOVIE, projection, selection, selArgs, null, null, sort);
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);
                return retCursor;

            }
            // Try to combine also
            case MOVIE_WITH_ID : {
                retCursor = mDbHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_MOVIE, projection, MovieContract.MovieEntry.COLUMN_ID, new String[]{String.valueOf(ContentUris.parseId(uri))}, null, null, sort);
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);

                return retCursor;

            }
            default:throw new UnsupportedOperationException("Unsupported Uri :"+uri);
        }


    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Uri returnUri;
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
            {
                long _id=db.insert(MovieContract.MovieEntry.TABLE_MOVIE,null,contentValues );
                if (_id>0)
                {
                    returnUri=MovieContract.MovieEntry.buildMoviesUri(_id);

                }
                else
                    throw new android.database.SQLException("Unsupported URI :" + uri);
                    break;

            }
            default:
                throw new UnsupportedOperationException("Unknown URI :"+uri);


        }
        getContext().getContentResolver().notifyChange(uri, null);


        db.close();
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int rowsDeleted;
//        This will delete all rows returning the number of rows deleted
//        if(null==selection)
//            selection="1"
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_MOVIE,selection,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI : "+uri);

        }

        //
        if(rowsDeleted!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {

        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int rowsUpdated;
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_MOVIE,contentValues,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI :"+uri);
        }
        if(rowsUpdated!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowsUpdated;

    }

}
