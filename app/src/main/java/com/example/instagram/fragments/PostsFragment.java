package com.example.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.EndlessRecyclerViewScrollListener;
import com.example.instagram.PostAdapter;
import com.example.instagram.R;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostAdapter postAdapter;
    protected List<Post> mPostsList;
    private SwipeRefreshLayout swipeContainer;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    // onCreateView to inflate the view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // create the data source
        mPostsList = new ArrayList<>();

        // create the adapter
        postAdapter = new PostAdapter(getContext(), mPostsList);

        // set the adapter on the recycler view
        rvPosts.setAdapter(postAdapter);

        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(linearLayoutManager);

        // retain an instance so that you can call 'resetState()' for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // triggered only when new data needs to be appended to the list
                // add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        // adds the scroll listener to recycler view
        rvPosts.addOnScrollListener(scrollListener);

        // setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(false);
                fetchHomeAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);

        // specify what you want to include by referencing the key
        postQuery.include(Post.KEY_USER);

        // set limit for how many posts to retrieve
        postQuery.setLimit(20);

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

    public void fetchHomeAsync(int page) {
        // Remember to CLEAR OUT old items before appending in the new ones
        postAdapter.clear();

        // refresh the view so that all the new tweets and updates come in
        queryPosts();

        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.setSkip(20);
    }
}