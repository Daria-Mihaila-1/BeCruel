package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    public PostRecyclerAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView username;
        TextView timeStamp;
        ImageButton commentBtn;
        ImageButton reactBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.ivPost);
            username = itemView.findViewById(R.id.ivUsername);
            timeStamp = itemView.findViewById(R.id.ivTimeStamp);
            commentBtn = itemView.findViewById(R.id.ivComment);
            reactBtn = itemView.findViewById(R.id.ivReact);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.username.setText(post.getUsername());
        holder.postImage.setImageResource(post.getPostImage());
        holder.timeStamp.setText(post.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



}
