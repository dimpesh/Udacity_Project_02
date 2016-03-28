package com.movies.app.popularmovies.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by DIMPESH : ${month}
 */
public class TestDb extends AndroidTestCase
{

    //  / is added to make sure UI is getting correctly
    private static final String TEST_MOVIE="/My Movie";
    public static final String LOG_TAG=TestDb.class.getSimpleName();


    // Test should start with clean database...
    void deleteThedatabase()
    {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    /*

    This must be called before test is started to delete the database...
    so test is always clean test ...
     */

    public void setUp()
    {
        deleteThedatabase();
    }


    public void testCreateDb() throws Throwable
    {
        // Since we have only one table no need of Hash Tag...
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db=new MovieDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // creating HashSet of
        final HashSet<String> tableNameHashSet=new HashSet<String>();
        tableNameHashSet.add(MovieContract.MovieEntry.TABLE_MOVIE);

// Comment for tables creation which we want....

    Cursor c=db.rawQuery("SELECT name from sqlite_master WHERE type='table'",null);
        assertTrue("Error : means DAtabase is not created...",c.moveToFirst());

        /// verify tables created

        do
        {
            tableNameHashSet.remove(c.getString(0));
        }while (c.moveToNext());

        // if this fails then our db doesnt contain movie table...

        assertTrue("DB is created without movie table...",tableNameHashSet.isEmpty());


        // checking table columns...

        c=db.rawQuery("PRAGMA table_info(" + MovieContract.MovieEntry.TABLE_MOVIE + ")",null);

        assertTrue("Error : means that we are unable to Query db for table...",c.moveToFirst());


        // Building HashSet of all cols we need to check...

        final HashSet<String> movieColumnHashSet= new HashSet<String>();
        movieColumnHashSet.add(MovieContract.MovieEntry._ID);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_TITLE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_MOVIE_ID);

        int columnIndex=c.getColumnIndex("name");
        do
        {
            String columnTitle=c.getString(columnIndex);
            movieColumnHashSet.remove(columnTitle);
        }while (c.moveToNext());

        // if this failed ... db doesn't contain all title...
        // entry cols...
        assertTrue("Error : db doesnt contain all of req cols...",movieColumnHashSet.isEmpty());
        db.close();

        }

    /*
    code to test inserting of values ...
     */


    public void testMovieTable()
    {
        //we use movieRowId to insert...

  //      long movieRowId=insertMovie();

        MovieDbHelper dbHelper=new MovieDbHelper(mContext);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

//        ContentValues movievalues=TestUtilities.createMovieValues(movieRowId);

        // Step 3 :INsert values
        ContentValues movievalues=new ContentValues();
        movievalues.put(MovieContract.MovieEntry._ID,0);
        movievalues.put(MovieContract.MovieEntry.COLUMN_TITLE,"My Movie");
        movievalues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"MOVIE OVERVIEW");
        movievalues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,"20161221");
        movievalues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,"7");
        movievalues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,"MOVIE_POSTER");
        movievalues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,"MOVIE_BACKDROP");
        movievalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,"MOVIEID");


        long movieRowNewId=db.insert(MovieContract.MovieEntry.TABLE_MOVIE,null,movievalues);
        assertTrue(movieRowNewId!=-1);

        // Step 4 :
        Cursor movieCursor = db.query(MovieContract.MovieEntry.TABLE_MOVIE,null,null,null,null,null,null);

        // Move to first and check to see if we have any...
        assertTrue("Error : No records returned", movieCursor.moveToFirst());

        // Step 5 : validate the Movie Query
        TestUtilities.validateCurrentRecord("Test Insert Movie Entry failed to validate ..", movieCursor, movievalues);

        // Move the curssor t demonstrate that only one record in db
        assertFalse("Error : more than one record returned",movieCursor.moveToNext());

        // Step 6 : close cursor and db

        movieCursor.close();
        db.close();


    }


    // function to insert the movie values.....
    public long insertMovie()
    {
        //  Step 1:  get reference to writable db...

        MovieDbHelper dbHelper=new MovieDbHelper(mContext);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        // Step 2 : get Values....
        ContentValues movieValues=new ContentValues();
        movieValues.put(MovieContract.MovieEntry._ID,0);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE,"My Movie");
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"MOVIE OVERVIEW");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,"20161221");
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,"7");
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,"MOVIE_POSTER");
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,"MOVIE_BACKDROP");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,"MOVIEID");



        // Step 3 : Insert values and get Row ID back...


        long movieRowId=db.insert(MovieContract.MovieEntry.TABLE_MOVIE,null,movieValues);

        // Verify we got a row back...

        assertTrue(movieRowId!=-1);


        // Now getting back the values ...

        Cursor cursor=db.query(MovieContract.MovieEntry.TABLE_MOVIE,null,null,null,null,null,null);

        // Move the cursor to valid row...

        assertTrue("Error : No records Returned ... ", cursor.moveToFirst());

        /// Step 5 : validate  data to original values ...

     //   TestUtilities.validateCurrentRecord("Error  : Location Query Validation Failed",cursor,movieValues);

     //   assertFalse("Error : Multiple record returned...", cursor.moveToFirst());

        /// Step 6 : close cursor and db...

        cursor.close();
        db.close();
        return movieRowId;
    }



}

