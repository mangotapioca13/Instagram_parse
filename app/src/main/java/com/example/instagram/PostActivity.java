package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;

import org.parceler.Parcels;

public class PostActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvTimeAgo;
    private TextView tvDescription;
    private ImageView ivPostImage;
    private ImageView ivProfileImage;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvTimeAgo = (TextView) findViewById(R.id.tvTimeAgo);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivPostImage = (ImageView) findViewById(R.id.ivPostImage);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        // extract post object from intent extras
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        // use post extra to populate data into views
        tvUsername.setText(post.getUser().getUsername());
        tvTimeAgo.setText("Posted " + post.getTimeAgo());
        tvDescription.setText(post.getDescription());

        if (post.getImage() != null) {
            Glide.with(this)
                    .load(post.getImage().getUrl())
                    .into(ivPostImage);
        }
    }
}