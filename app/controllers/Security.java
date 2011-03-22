package controllers;

import models.*;

public class Security extends Secure.Security {
	/**
	 * Authenticates the current user. If their user nad passowrd
	 * match what is recoreded in the database then they are connected
	 * and given enhanced privledges according to their rank.
	 * In this system only the concept of administrators exists, and 
	 * normal users are not given passwords since we have decided to 
	 * implement email based security.
	 * 
	 * The method is called by all of the authentication pages
	 * 
	 * @param email
	 * @param password
	 */
    static boolean authenticate(String email, String password) {
        return User.connect(email, password) != null;
    }
    
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byEmail", connected()).<User>first().isAdmin;
        }
        return false;
    }
}
