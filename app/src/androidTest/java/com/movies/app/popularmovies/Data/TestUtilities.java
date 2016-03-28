package com.movies.app.popularmovies.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by DIMPESH : ${month}
 */
public class TestUtilities extends AndroidTestCase{

    static void validateCursor(String error,Cursor valueCursor,ContentValues expectedValues)
    {
        assertTrue("Empty cursor returned " + error,valueCursor.moveToFirst());
        validateCurrentRecord(error,valueCursor,expectedValues);
        valueCursor.close();
    }
    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues)
    {
        Set<Map.Entry<String,Object>>valueSet=expectedValues.valueSet();

        for(Map.Entry<String,Object>entry:valueSet)
        {
            String columnName=entry.getKey();
            int idx=valueCursor.getColumnIndex(columnName);
            assertFalse("Column "+columnName+ " not found.." + error,idx==-1);
            String expectedValue=entry.getValue().toString();
            assertEquals("Value "+entry.getValue().toString()+" did not match.."+ expectedValue + ". "+error,expectedValue,valueCursor.getString(idx));


        }

    }

    public static ContentValues createMovieValues(long movieRowId)
    {
        ContentValues movieValues=new ContentValues();
        movieValues.put(MovieContract.MovieEntry._ID,movieRowId);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE,"My Movie");
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"MOVIE OVERVIEW");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,"20161221");
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,"7");
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,"MOVIE_POSTER");
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,"MOVIE_BACKDROP");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,"MOVIEID");

        return movieValues;
    }

    public static ContentValues createMovieValues()
    {

        ContentValues movieValues=new ContentValues();
        movieValues.put(MovieContract.MovieEntry._ID,100);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE,"My Movie");
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"MOVIE OVERVIEW");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,"20161221");
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,"7");
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,"MOVIE_POSTER");
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,"MOVIE_BACKDROP");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,"MOVIEID");

        return movieValues;
    }



}
