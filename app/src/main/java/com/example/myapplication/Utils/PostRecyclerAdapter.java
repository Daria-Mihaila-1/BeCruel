package com.example.myapplication.Utils;

<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Utils/PostRecyclerAdapter.java
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
=======
>>>>>>> master:app/src/main/java/com/example/myapplication/PostRecyclerAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Post;
<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Utils/PostRecyclerAdapter.java
import com.example.myapplication.R;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private final Context c;
    private final ArrayList<Post> posts;
    public PostRecyclerAdapter(ArrayList<Post> posts, Context context) {
=======

import java.util.ArrayList;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    public PostRecyclerAdapter(ArrayList<Post> posts) {
>>>>>>> master:app/src/main/java/com/example/myapplication/PostRecyclerAdapter.java
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
<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Utils/PostRecyclerAdapter.java

        if (post.getPostImage() !=null) {
            Bitmap bitmap= BitmapFactory.decodeByteArray(post.getPostImage(),0,post.getPostImage().length);
            // set bitmap on imageView
            holder.postImage.setImageBitmap(bitmap);


        }
=======
>>>>>>> master:app/src/main/java/com/example/myapplication/PostRecyclerAdapter.java

        //holder.username.setText(post.getUsername());
        //holder.postImage.setImageResource(post.getPostImage());
        holder.timeStamp.setText(post.getTimestamp());
<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Utils/PostRecyclerAdapter.java
        holder.crimeDescription.setText(post.getCrimeDescription());
        System.out.println(post.getCrimeDescription());

        GeoPoint postLocation = post.getLocation();
        Geocoder geocoder = new Geocoder(this.c, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(postLocation.getLatitude(), postLocation.getLongitude(), 1);
            String cityName = addresses.get(0).getAddressLine(0);
            holder.location.setText(cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
=======
>>>>>>> master:app/src/main/java/com/example/myapplication/PostRecyclerAdapter.java


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



}
