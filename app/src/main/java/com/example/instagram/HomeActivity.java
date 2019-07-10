package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";
    ArrayList<Post> postsList;
    RecyclerView rvPosts;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbarTop);

        // find RecyclerView and setup
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts = (RecyclerView) findViewById(R.id.rvPost);
        // rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // instantiate the arraylist (data source)
        postsList = new ArrayList<>();

        // construct the adapter form this data source
        postAdapter = new PostAdapter(postsList);

        // set the adapter
        rvPosts.setAdapter(postAdapter);

        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);

        // specify what you want to include by referencing the key
        postQuery.include(Post.KEY_USER);

        // use findInBackground instead of find since you don't want to take up the UI / main thread
        // with expensive operations (making a network call)
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                     // Log.d(TAG, "Post: " + post.getDescription()
                     //   + ", Username: " + post.getUser().getUsername());

                    postsList.add(post);
                    postAdapter.notifyItemInserted(posts.size() - 1);
                }
            }
        });
    }

    // methods to handle click on the LogOut menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the tool bar if it is present
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miLogOut) {
            Toast.makeText(this, "LogOut Selected", Toast.LENGTH_SHORT).show();
            ParseUser.logOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.miCreate) {
            Intent intent = new Intent(HomeActivity.this, PostActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}