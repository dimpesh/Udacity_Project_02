package com.movies.app.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by DIMPESH : ${month}
 */
public class MovieCursorAdapter extends CursorAdapter{

    public static final String LOG_TAG=MovieCursorAdapter.class.getSimpleName();
    private Context mContext;
    private int loader_id;
    public MovieCursorAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
        mContext=context;
        loader_id=flags;
        Log.v("MovieCursorAdapter","-------------------Constructor Called");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        Log.v("MovieCursorAdapter","------------newViewCalled");
        View view= LayoutInflater.from(context).inflate(R.layout.grid_item_movies,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        Log.v("MovieCursorAdapter"," : Bind View Called");

        String baseImageUrl="http://image.tmdb.org/t/p/w185/";

        String str=baseImageUrl+cursor.getString(MainActivityFragment.COL_POSTER_PATH);
        Log.v("URL MCA :",str);

        ImageView poster= (ImageView) view.findViewById(R.id.grid_item_movies_imageview);
        Picasso.with(context).load(str).placeholder(R.mipmap.img_placeholder).into(poster);
  //          poster.setImageResource(R.mipmap.img_placeholder);
    }

    // tried implementing getCount... can be replaced...

    @Override
    public int getCount() {

        if(getCursor()==null)
            return 0;
        return super.getCount();
    }
}
