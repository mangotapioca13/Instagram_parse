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
        postQuery.setLimit(page_size);

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

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromParse(final int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        final ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);

        // specify what you want to include by referencing the key
        postQuery.include(Post.KEY_USER);

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

                // start adding all the older posts after the current oldest post
                // until you reach the end of all posts or you reach
                for (int i = page_size * offset; i < posts.size(); i ++) {
                    if (i >= (page_size * (offset + 1))) {
                        break;
                    }

                    Post post = posts.get(i);
                    mPostsList.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }
        });
    }
}