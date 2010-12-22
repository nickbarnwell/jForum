package controllers;

import models.*;

public class Security extends Secure.Security {
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
