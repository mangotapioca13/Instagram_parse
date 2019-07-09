package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsetagram")
                .clientKey("parsetagram07082019")
                .server("https://parsetagram-angela.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}