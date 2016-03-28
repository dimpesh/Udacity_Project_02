package com.movies.app.popularmovies;
import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by DIMPESH : ${month}
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        // Create an Initialiser Builder
        Stetho.InitializerBuilder initializerBuilder=Stetho.newInitializerBuilder(this);

        // Enabling Chrome Dev Tools
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));

        // Enabling command line interface
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));


        // Using initialiseBuilder  to  generate  an initialiser
        Stetho.Initializer initializer=initializerBuilder.build();

        // Initialise stetho with  initialiser

        Stetho.initialize(initializer);

    }
}
