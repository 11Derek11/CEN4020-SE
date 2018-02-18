package com.example.redent0r.ethernal;

import org.junit.Test;

import static org.junit.Assert.*;


public class UserUnitTest {
    @Test
    public void testGetters() throws Exception {
        String testName = new String("Ashley");
        User testUser = new User(testName);
        assertEquals(testUser.getName(), testName);

        String emptyString = new String("");
        User emptyUser = new User(emptyString);
        assertEquals(emptyUser.getName(), emptyString);
    }
}