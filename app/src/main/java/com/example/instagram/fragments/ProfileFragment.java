package com.example.instagram.fragments;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    @Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);

        // specify what you want to include by referencing the key
        postQuery.include(Post.KEY_USER);

        // set limit for how many posts to retrieve
        postQuery.setLimit(20);

        // control posts that are made by the user
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

        // order how the posts are displayed
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);

        // use findInBackground instead of find since you don't want to take up the UI / main thread
        // with expensive operations (making a network call)
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                mPostsList.addAll(posts);
                postAdapter.notifyDataSetChanged();
            }
        });
    }
}