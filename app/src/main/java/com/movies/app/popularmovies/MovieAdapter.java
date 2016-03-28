package com.movies.app.popularmovies;

import android.content.Context;
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
public class MovieAdapter extends ArrayAdapter {

//    public MovieAdapter(Context context, int grid_item_movies, int resource, List<String> listmovie) {
//        super(context,R.layout.fragment_main,resource);

    public MovieAdapter(Context context, int grid_item_movies, int resource, List<MovieObject> listmovie) {
        super(context,R.layout.fragment_main,resource);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        String poster_path=getItem(position).toString();
            MovieObject movieObj= (MovieObject) getItem(position);
        String poster_path=movieObj.poster_path;
        String baseImageUrl="http://image.tmdb.org/t/p/w185/";
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movies, parent, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_movies_imageview);
            Picasso.with(getContext()).load(baseImageUrl + poster_path).placeholder(R.mipmap.img_placeholder).into(imageView);
            return convertView;
    }
}
