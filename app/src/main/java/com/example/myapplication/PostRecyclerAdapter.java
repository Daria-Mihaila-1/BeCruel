package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private final Context c;
    private final ArrayList<Post> posts;
    public PostRecyclerAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.c = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;

        TextView timeStamp;
        TextView location;
        TextView crimeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.locationIV);
            postImage = itemView.findViewById(R.id.ivPost);
            timeStamp = itemView.findViewById(R.id.timeStampIV);
            crimeDescription = itemView.findViewById(R.id.crimeDescriptionTV);

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

        if (post.getPostImage() !=null) {
            Bitmap bitmap= BitmapFactory.decodeByteArray(post.getPostImage(),0,post.getPostImage().length);
            // set bitmap on imageView
            holder.postImage.setImageBitmap(bitmap);


        }

        holder.timeStamp.setText(post.getTimestamp());
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


    }
    @Override
    public int getItemCount() {
        return posts.size();
    }



}
