package com.movies.app.popularmovies;

/**
 * Created by DIMPESH : ${month}
 */
public class TrailerObject
{
    String id;
    String name;
    String key;

    public void TrailerObject()
    {}
    public void TrailerObject(String key)
    {
        this.key=key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }



    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
