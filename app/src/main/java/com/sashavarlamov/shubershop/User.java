package com.sashavarlamov.shubershop;

/**
 * Created by sashaadmin on 6/20/15.
 */
public class User {
    public final String firstName;
    public final String lastName;
    public final String mail;
    public final String email;
    public final boolean isConsumer;

    public User(String fn, String ln, String m, String em, boolean isc) {
        this.firstName = fn;
        this.lastName = ln;
        this.mail = m;
        this.email = em;
        this.isConsumer = isc;
    }
}
