package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.myapplication.Utils.LogInAndRegistrationDataValidator;

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
}