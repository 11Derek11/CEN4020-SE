package com.example.redent0r.ethernal;

import org.junit.Test;

import static org.junit.Assert.*;


public class UserUnitTest {
    @Test
    public void testGetters() throws Exception {
        String testName = "Ashley";
        String testAddress = "0x5abfec25f74cd88437631a7731906932776356f9";
        User testUser = new User(testName, testAddress);
        assertEquals(testUser.getName(), testName);

        String emptyString = "";
        User emptyUser = new User(emptyString, testAddress);
        assertEquals(emptyUser.getName(), emptyString);
    }
}