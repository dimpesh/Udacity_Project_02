package com.movies.app.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DIMPESH : ${month}
 */
public class TrailerAdapter extends ArrayAdapter<TrailerObject> {
    public TrailerAdapter(Context context, int resource, int textViewResourceId,List<TrailerObject>trailerObject) {
        super(context, R.layout.fragment_main, resource);
    }

    String LOG_TAG=TrailerAdapter.class.getSimpleName();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrailerObject trailerObject = getItem(position);
        String baseUrl = "http://img.youtube.com/vi/";
        String endUrl = "/1.jpg";
        String key=trailerObject.getKey();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item_imageview, parent, false);
        }
        ImageView trailerView = (ImageView) convertView.findViewById(R.id.gridview_movies_imageview);

//        Log.v(LOG_TAG,baseUrl+trailerObject.key+endUrl);
        Log.v(LOG_TAG, baseUrl+key+endUrl);
       Picasso.with(getContext()).load(baseUrl+key+endUrl).placeholder(R.mipmap.img_placeholder).into(trailerView);
        return convertView;
    }

}
