package com.example.instagram;

import android.app.Application;

import com.example.instagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // register a subclass so parse knows that this is a custom parse model
        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsetagram")
                .clientKey("parsetagram07082019")
                .server("https://parsetagram-angela.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}