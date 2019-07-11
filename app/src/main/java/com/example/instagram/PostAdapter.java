package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> mPostsList;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.mPostsList = posts;
    }

    // with the data at the given position, bind it to the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPostsList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostsList.size();
    }

    public void clear() {
        mPostsList.clear();
        notifyDataSetChanged();
    }

    // creates one individual row in the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUserName;
        private TextView tvTimeAgo;
        private TextView tvDescription;
        private ImageView ivPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // perform findViewById lookups
            tvUserName = (TextView) itemView.findViewById(R.id.tvUsername);
            tvTimeAgo = (TextView) itemView.findViewById(R.id.tvTimeAgo);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);

            // attach a click listener to the entire row view
            itemView.setOnClickListener((View.OnClickListener)this);
        }

        public void bind(Post post) {
            tvUserName.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            tvTimeAgo.setText("Posted " + post.getTimeAgo());

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivPostImage);
            }
        }

        // handles the row being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = mPostsList.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }
}