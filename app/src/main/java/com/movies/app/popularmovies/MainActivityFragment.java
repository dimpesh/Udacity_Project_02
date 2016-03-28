package com.movies.app.popularmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.movies.app.popularmovies.Data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String STATE_ACTIVATED_POSITION="activated_position";


    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = GridView.INVALID_POSITION;
    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(MovieObject m);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(MovieObject m) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainActivityFragment() {
    }


    // Declaring MyCursorAdapter variable and using it in programmin..
    public MovieCursorAdapter movieCursorAdapter;
    // CursorLoader Implementation Step 1, create Loader ID
    private static final int MOVIE_LOADER=0;
    // Adding String [] columns.
    private static final String[] MOVIE_COLUMNS=
            {
                            MovieContract.MovieEntry.COLUMN_ID,
                            MovieContract.MovieEntry.COLUMN_TITLE,
                            MovieContract.MovieEntry.COLUMN_OVERVIEW,
                            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
                            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
                            MovieContract.MovieEntry.COLUMN_MOVIE_ID
            };

    // Defining Colummn Indices....
    static final int COL_ID=0;
    static final int COL_TITLE=1;
    static final int COL_OVERVIEW=2;
    static final int COL_RELEASE_DATE=3;
    static final int COL_VOTE_AVERAGE=4;
    static final int COL_POSTER_PATH=5;
    static final int COL_BACKDROP_PATH=6;
    static final int COL_MOVIE_ID=7;
    // UPTIL HERE
    MovieObject movieClicked;

    String popular="popularity.desc";
    public boolean favMenuSelected;
    public boolean popMenuselected=true;
    String top="vote_average.desc";
    List<MovieObject>listmovie;
     MovieObject []movieObjects;
    private MovieAdapter movieAdapter;
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
     //   super.onCreateOptionsMenu(menu, inflater);
       inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_popular)
        {
            favMenuSelected=false;
            popMenuselected=true;
            gridView.setAdapter(movieAdapter);
            new FetchMovieTask().execute(popular);
            return true;

        }
        if(id==R.id.action_top_rated)
        {
            favMenuSelected=false;
            popMenuselected=false;
            gridView.setAdapter(movieAdapter);
            new FetchMovieTask().execute(top);
            return true;
        }

        if(id==R.id.action_favourite)
        {
            favMenuSelected=true;
            getLoaderManager().restartLoader(MOVIE_LOADER,null,this);

            return true;
//            Toast.makeText(getContext(),"Favourite Called...",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);

    }



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //String []moviesArray= {"MONDAY","TUESDAY","WENESDAY","THURSDAY"};
        movieObjects= new MovieObject[]{new MovieObject("a"), new MovieObject("b")};
        //List<String>listmovie=new ArrayList<String>(Arrays.asList(moviesArray));
        listmovie=new ArrayList<MovieObject>(Arrays.asList(movieObjects));
        //movieAdapter=new MovieAdapter(getActivity(),R.layout.grid_item_movies,R.id.grid_item_movies_imageview,listmovie);
        movieAdapter=new MovieAdapter(getActivity(),R.layout.grid_item_movies,R.id.grid_item_movies_imageview,listmovie);
        View rootview=inflater.inflate(R.layout.fragment_main,container,false);
        new FetchMovieTask().execute("popularity.desc");
        gridView= (GridView) rootview.findViewById(R.id.gridview_movies);

        // CursorAdapter work...
        String url="content://com.movies.app.popularmovies.app/movie";

        Uri fetchUri=Uri.parse(url);
        Cursor c= getContext().getContentResolver().query(fetchUri, null, null, null, null);
//        if (c.moveToFirst()) {
//            do {
//                {
//                        String result = "S. NUMBER           : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry._ID))
//                            + "\nMOVIE ID             : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID))
//                            + "\nMOVIE TITLE          : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE))
//                            +"\nMOVIE OVERVIEW       : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW))
//                            +"\nMOVIE RELEASE DATE   : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))
//                            +"\nMOVIE VOTE           : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE))
//                            +"\nMOVIE POSTER         : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH))
//                            +"\nMOVIE BACKDROP       : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)).toString();
//
//                    Log.v("RESULT_QUERY VERBOSE", result);
//                }
//            } while (c.moveToNext());
//        }
// Uptil Here...


//    movieCursorAdapter=new MovieCursorAdapter(getContext(),c,MOVIE_LOADER);

        if(!favMenuSelected)
            gridView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//
//                if (favMenuSelected == false) {
//                    movieClicked = (MovieObject) movieAdapter.getItem(position);
//                    //String overview=movieClicked.overview;
//                    //Toast.makeText(getActivity(),overview,Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(getActivity(), DetailActivity.class);
////l4                Bundle mBundle=new Bundle();
//
////                mBundle.putSerializable("MovieObjectSent",movieClicked);
////                intent.putExtras(mBundle);
//
//                    intent.putExtra("data", movieClicked);
//                    startActivity(intent);
//
//                } else {
//
//                    Cursor cursor= (Cursor) adapterView.getItemAtPosition(position);
//                    MovieObject mo=new MovieObject();
//
//                    mo.title=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
//                    mo.id=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
//                    mo.poster_path=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
//                    mo.backdrop_path=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
//                    mo.vote_average=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
//                    mo.release_date=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
//                    mo.overview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
//                    Log.v("Movie Detail :",cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
//
//                    Intent intent=new Intent(getActivity(),DetailActivity.class);
//                    intent.putExtra("data",mo);
//                    startActivity(intent);
//                }
//
//
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (favMenuSelected == false) {
                    movieClicked = (MovieObject) movieAdapter.getItem(position);
                    Log.v("ABC", movieClicked.toString());

                    mCallbacks.onItemSelected(movieClicked);


                } else {

                    Cursor cursor= (Cursor) adapterView.getItemAtPosition(position);
                    MovieObject mo=new MovieObject();
                    Log.v("ABC",mo.toString());
                    mo.title=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                    mo.id=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                    mo.poster_path=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
                    mo.backdrop_path=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
                    mo.vote_average=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
                    mo.release_date=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                    mo.overview=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                    Log.v("Movie Detail :",cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));

                    mCallbacks.onItemSelected(mo);
                }


            }
        });

        return rootview;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v("onCreateLoader :", "-----------------------Called Successfully-----------------");

        String url="content://com.movies.app.popularmovies.app/movie";

        Uri fetchUri=Uri.parse(url);
        // try fetch URI if failed.
//        Uri movieUri= MovieContract.MovieEntry.buildMoviesUri(Integer.parseInt(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        return new CursorLoader(getActivity(),fetchUri,MOVIE_COLUMNS,null,null,null) ;
    }

    // onLoadFinihed is called when the Data is Ready...
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c)
    {
        Log.v("onLoadFinished","-----------------------Called Successfully-----------------");
        // This Works...
//        if (c.moveToFirst()) {
//            do {
//                {
//                    String result = "S. NUMBER(onLoadFinished)           : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry._ID))
//                            + "\nMOVIE ID(onLoadFinished)             : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID))
//                            + "\nMOVIE TITLE(onLoadFinished)          : " + c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE))
//                            +"\nMOVIE OVERVIEW(onLoadFinished)       : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW))
//                            +"\nMOVIE RELEASE DATE(onLoadFinished)   : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))
//                            +"\nMOVIE VOTE(onLoadFinished)           : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE))
//                            +"\nMOVIE POSTER(onLoadFinished)         : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH))
//                            +"\nMOVIE BACKDROP(onLoadFinished)       : "+c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)).toString();
//
//                    Log.v("RESULT_QUERY VERBOSE", result);
//                }
//            } while (c.moveToNext());
//        }
        if(movieCursorAdapter==null)
            movieCursorAdapter=new MovieCursorAdapter(getContext(),c,MOVIE_LOADER);
        movieCursorAdapter.swapCursor(c);
        Log.v("Favourite VERBOSE", String.valueOf(favMenuSelected));

        if(favMenuSelected)
            gridView.setAdapter(movieCursorAdapter);
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        Log.v("onLoaderReset","-----------------------Called Successfully-----------------");

        movieCursorAdapter.swapCursor(null);
    }


    class FetchMovieTask extends AsyncTask<String,Void,MovieObject[]>
    {
        ProgressDialog dialog=new ProgressDialog(getActivity());
        ContentLoadingProgressBar progressBar=new ContentLoadingProgressBar(getContext());
        MovieObject [] movieObjects=null;
   //     String []str=null;

        @Override
        protected void onPostExecute(MovieObject[] str) {
            if(str==null)
                return;
        if(str!=null)
                movieAdapter.clear();

                for (MovieObject m : str) {
                    movieAdapter.add(m);
                }

            if(dialog.isShowing()==true)
            {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.show();

        }

        @Override
        protected MovieObject[] doInBackground(String... strings) {
            final String movieBaseUrl="http://api.themoviedb.org/3/discover/movie?";
            String api_key=BuildConfig.MyTmdbApiKey;
            String API_PARAM="api_key";
            String TYPE_PARAM="sort_by";
            String YEAR_PARAM="primary_release_year";
            String year="2015";
            String type=strings[0];
            String TYPE_VOTE_COUNT="vote_count.gte";
            String voteCount="50";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJSONStr = null;
            try {

                Uri buildUri=Uri.parse(movieBaseUrl).buildUpon().appendQueryParameter(TYPE_VOTE_COUNT,voteCount).appendQueryParameter(TYPE_PARAM,type).appendQueryParameter(API_PARAM,api_key).build();
                Log.v("URL",buildUri.toString());
                URL url=new URL(buildUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    movieJSONStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    movieJSONStr = null;
                }
                movieJSONStr = buffer.toString();
                String title="title";
                String vote_average="vote_avergae";
                String overview="overview";
                JSONObject movieJSONObject=new JSONObject(movieJSONStr);
                JSONArray movieJSONArray=movieJSONObject.optJSONArray("results");
                movieObjects=new MovieObject[movieJSONArray.length()];
                for(int i=0;i<movieJSONArray.length();i++)
                {
                    movieObjects[i]=new MovieObject();
                    JSONObject jsonObject=movieJSONArray.getJSONObject(i);
                    movieObjects[i].title=jsonObject.optString("title").toString();
                    movieObjects[i].overview=jsonObject.optString("overview").toString();
                    movieObjects[i].poster_path=jsonObject.optString("poster_path").toString();
                    movieObjects[i].release_date = jsonObject.getString("release_date").toString();
                    movieObjects[i].vote_average=jsonObject.getString("vote_average").toString();
                    movieObjects[i].backdrop_path=jsonObject.getString("backdrop_path").toString();
                    movieObjects[i].id=jsonObject.getString("id").toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieObjects;


        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        if(popMenuselected)
//            new FetchMovieTask().execute(popular);
//            else
//        new FetchMovieTask().execute(top);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != GridView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }
    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        Log.v("ABC : ","setActivatedOnItemClick Executed");

        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        gridView.setChoiceMode(activateOnItemClick
                ? GridView.CHOICE_MODE_SINGLE
                : GridView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {

        Log.v("ABC : ","setActivatedPosition Executed");
        if (position == GridView.INVALID_POSITION) {
            gridView.setItemChecked(mActivatedPosition, false);
        } else {
            gridView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }




}
