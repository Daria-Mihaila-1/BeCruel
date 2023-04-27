package com.example.myapplication.Utils;

import com.example.myapplication.Entities.Post;

import java.util.ArrayList;

public class PostUtils {
    private final ArrayList<Post> posts;

    public PostUtils(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> postsWithLocationAndWithoutImageLast(){
        ArrayList<Post> postsContainingImage = new ArrayList<>();

        for (Post post: this.posts){
            if (post.getLocation_latitude() != 0.0 && post.getLocation_longitude()!=0.0){

                if(post.getPostImage() != null){
                    postsContainingImage.add(0, post);
                }
                else{
                    postsContainingImage.add(post);
                }

            }
        }

        return postsContainingImage;
    }


}
