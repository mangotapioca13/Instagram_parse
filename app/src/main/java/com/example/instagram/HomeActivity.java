package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private EditText etDescription;
    private Button btnCreate;
    private Button btnTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDescription = (EditText) findViewById(R.id.etDescription);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);

        queryPosts();

//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String description = etDescription.getText().toString();
//                final ParseUser user = ParseUser.getCurrentUser();
//
//                final File file = new File(imagePath);
//                final ParseFile parseFile = new ParseFile(file);
//
//                createPost(description, parseFile, user);
//            }
//        });
//
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTopPosts();
//            }
//        });
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
                    Log.d("HomeActivity", "Post: " + post.getDescription()
                        + ", Username: " + post.getUser().getUsername());
                }
            }
        });
    }

//    private void createPost(String description, ParseFile imageFile, ParseUser user) {
//        final Post newPost = new Post();
//        newPost.setDescription(description);
//        newPost.setImage(imageFile);
//        newPost.setUser(user);
//
//        newPost.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("HomeActivity", "Create post success!");
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public void loadTopPosts() {
//        // create new Post.Query
//        final Post.Query postsQuery = new Post.Query();
//        postsQuery.getTop().withUser();
//
//        // grabs all posts in the background thread
//        postsQuery.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, ParseException e) {
//                if (e == null) {
//                    for (int i = 0; i < objects.size(); i++) {
//                        Log.d("HomeActivity", "Post[" + i + "] = "
//                                + objects.get(i).getDescription()
//                                + "\nusername = " + objects.get(i).getUser().getUsername());
//                    }
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    // methods to handle click on the LogOut menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toast.makeText(this, "LogOut Selected", Toast.LENGTH_SHORT).show();
        ParseUser.logOut();
        finish();
        return super.onOptionsItemSelected(item);
    }
}