package com.example.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    // pass in the posts array into the constructor
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        // populate the views according to this data
        holder.tvUserName.setText(post.getUser().getUsername());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPostImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUserName;
        public TextView tvTimeAgo;
        public ImageView ivPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // perform findViewById lookups
            tvUserName = (TextView) itemView.findViewById(R.id.tvUsername);
            tvTimeAgo = (TextView) itemView.findViewById(R.id.tvTimeAgo);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
        }
    }
}