package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.Entities.Post;
import com.example.myapplication.Utils.LogInAndRegistrationDataValidator;
import com.example.myapplication.Utils.PostUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void email_isCorrect() {


        LogInAndRegistrationDataValidator validator = new LogInAndRegistrationDataValidator();

        // Boundary Value Analysis (BVA)
        //tests for the length of the password
        // too little characters(min-1)
        assertFalse(validator.isPasswordValid("Cosmi#2"));

        //ok password (nominal value)
        assertTrue(validator.isPasswordValid("Cosmin#2002"));

        //exactly 8 char(min)
        assertTrue(validator.isPasswordValid("Cosmin#2"));

        // more than 20 char (more + 1)
        assertFalse(validator.isPasswordValid("Peste20deCaractere#nu"));


        //Equivalence Class Partitioning (ECP)
        //before the @ symbol, there have to be [1, 64] characters
        //<1 class
        assertFalse(validator.isEmailValid("@yahoo.com"));
        //[1,64] class
        assertTrue(validator.isEmailValid("cosmin@yahoo.com"));
        //[1,64] class
        assertTrue(validator.isEmailValid("fkdasgfhdbsahkfvhkdsagf@yahoo.com"));
        //64< class
        assertFalse(validator.isEmailValid(
                "aaaaaaaaaa" +
                "aaaaaaaaaa" +
                "aaaaaaaaaa" +
                "aaaaaaaaaa" +
                "aaaaaaaaaa" +
                "aaaaaaaaaa" +
                "aaaaa@yahoo.com"));

    }

    @Test
    public void testPostsWithLocationAndWithoutImageLast() {

        // Create some sample posts with different locations and images
        Post post1 = new Post(null, new GeoPoint(1.0, 2.0), new Timestamp(12345, 0), "Test1");
        Post post2 = new Post(null, new GeoPoint(0.0, 0.0), new Timestamp(12346, 0), "Test2");
        Post post3 = new Post(new byte[]{1, 2, 3}, new GeoPoint(3.0, 4.0), new Timestamp(12347, 0), "Test3");
        Post post4 = new Post(new byte[]{4, 5, 6}, new GeoPoint(5.0, 6.0), new Timestamp(12348, 0), "Test4");
        Post post5 = new Post(new byte[]{7, 8, 9}, new GeoPoint(0.0, 7.0), new Timestamp(12349, 0), "Test5");

        // Add the posts to an ArrayList
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);

        // Create a PostUtils object with the ArrayList of posts
        PostUtils postUtils = new PostUtils(posts);

        // Call the postsWithLocationAndWithoutImageLast method and store the result
        ArrayList<Post> result = postUtils.postsWithLocationAndWithoutImageLast();

        // Define the expected result - posts with location and without image, ordered by timestamp (descending)
        ArrayList<Post> expected = new ArrayList<>();
        expected.add(post4);
        expected.add(post3);
        expected.add(post1);

        // Assert that the result matches the expected result
        assertEquals(expected, result);
    }

}