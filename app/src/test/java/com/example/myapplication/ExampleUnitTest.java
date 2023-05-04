package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.Entities.Post;
import com.example.myapplication.Utils.LogInAndRegistrationDataValidator;
import com.example.myapplication.Utils.PostUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import org.junit.Before;
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

    private PostUtils postUtils;
    private ArrayList<Post> posts;

    @Before
    public void setUp() {
        posts = new ArrayList<>();
        Post post1 = new Post(new GeoPoint(10.0, 20.0), null, null);
        Post post2 = new Post(new GeoPoint(0.0, 0.0), null, null);
        Post post3 = new Post(new GeoPoint(30.0, 40.0), null, null);
        Post post4 = new Post(new GeoPoint(0.0, 50.0), null, null);
        Post post5 = new Post(new GeoPoint(60.0, 0.0), null, null);
        Post post6 = new Post(new GeoPoint(70.0, 80.0), null, null);

        // add the posts to the list
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);

        postUtils = new PostUtils(posts);
    }

    @Test
    public void testPostsWithLocationAndWithoutImageLastStatementCoverage() {
        ArrayList<Post> result = postUtils.postsWithLocationAndWithoutImageLast();

        // check that the size of the result is correct
        assertEquals(4, result.size());

        // check that the posts with location and without image are last
        assertEquals(posts.get(0), result.get(0));
        assertEquals(posts.get(2), result.get(1));
        assertEquals(posts.get(4), result.get(2));
        assertEquals(posts.get(5), result.get(3));
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
        ArrayList<Post> posts1 = new ArrayList<>();
        posts1.add(post1);
        posts1.add(post2);
        posts1.add(post3);
        posts1.add(post4);
        posts1.add(post5);

        // Create a PostUtils object with the ArrayList of posts
        PostUtils postUtils = new PostUtils(posts1);

        // Call the postsWithLocationAndWithoutImageLast method and store the result
        ArrayList<Post> result = postUtils.postsWithLocationAndWithoutImageLast();

        // Define the expected result - posts with location and without image, ordered by timestamp (descending)
        ArrayList<Post> expected1 = new ArrayList<>();
        expected1.add(post4);
        expected1.add(post3);
        expected1.add(post1);

        // Assert that the result matches the expected result
        assertEquals(expected1, result);
    }

}