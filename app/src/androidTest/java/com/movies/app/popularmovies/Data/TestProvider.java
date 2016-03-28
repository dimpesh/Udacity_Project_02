package com.movies.app.popularmovies.Data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by DIMPESH : ${month}
 */
public class TestProvider extends AndroidTestCase
{
    public static final String LOG_TAG=TestProvider.class.getSimpleName();


    public void deleteAllRecordFromProvider()
    {
        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null);

        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI
        ,null
        ,null);

        Cursor cursor=mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        assertEquals("Error : Records not deleted from Weather during test",0,cursor.getCount());
        cursor.close();

    }

    public void deleteAllRecordsFromDb()
    {
        MovieDbHelper movieDbHelper=new MovieDbHelper(mContext);
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        db.delete(MovieContract.MovieEntry.TABLE_MOVIE, null, null);
        db.close();
    }

    public void deleteAllRecords()
    {
        deleteAllRecordsFromDb();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    // Checking Provider is registered Correctly...
    public void testProviderRegistry()
    {
        PackageManager pm=mContext.getPackageManager();
        // We define component based on pkg and weather provider class

        ComponentName componentName=new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());

        try
        {
            // Fetch provider info from component name from Contract

            ProviderInfo providerInfo=pm.getProviderInfo(componentName,0);
            // make sure the registered authority matches authority from contract...

            assertEquals("Error MovieProvider registered to authority :"
            +providerInfo.authority
            +"instead of authority "+ MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority,MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            assertTrue("Error : Weather provider not registered at "+
            mContext.getPackageName(),
                    false);

        }

    }


    /*

    This test does not test the database...
    It varifies ContentProvider returns correct type for each type of uri that it can handle
     */

    public void testGetType()
    {
        // content://com.movies.app.popularmovies.app/movies/;

        String type=mContext.getContentResolver().getType(MovieContract.MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir.com/com.movies.app.popularmovies.app/movies;
//        assertEquals(MovieContract.MovieEntry.CONTENT_DIR_TYPE,type);

        // THIS CASE IS NOT RUNNING
        assertEquals("Error : the MovieEntry CONTENT_URI should return "
        + MovieContract.MovieEntry.CONTENT_DIR_TYPE,type);
        Log.d(LOG_TAG,type);
        long movieId=261432;
        String itemType=mContext.getContentResolver().getType(MovieContract.MovieEntry.buildMoviesUri(movieId));
        assertEquals(MovieContract.MovieEntry.CONTENT_ITEM_TYPE,itemType);
        Log.d(LOG_TAG,itemType);

        // String



    }
}
