package com.movies.app.popularmovies.Data;

import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by DIMPESH : ${month}
 */
public class TestWeatherContract extends AndroidTestCase
{
    private static final long id=0;
    public void testBuildMovie()
    {
        Uri movieUri=MovieContract.MovieEntry.buildMoviesUri((id));
        assertNotNull("Null Uri returned , must fill in buildmovie in" +"MovieContract."+movieUri);
        Log.v("VERBOSE",movieUri.toString());
    //    assertEquals("Error : movie not properly appended in uri", movieUri.getLastPathSegment());

//        assertEquals("Error : Movie Uri doesnt match result",movieUri.toString(),MovieContract.BASE_CONTENT_URI);


    }
}
