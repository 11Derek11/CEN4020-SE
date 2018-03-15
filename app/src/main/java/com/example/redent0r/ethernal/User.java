package com.example.redent0r.ethernal;

/**
 * @author redent0r
 *
 */

public class User {

    String name;
    String address;

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
