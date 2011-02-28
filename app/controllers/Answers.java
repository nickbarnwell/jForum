package controllers;

import controllers.CRUD;
import controllers.Check;
import controllers.Secure;
import play.*;
import play.mvc.*;
@Check("admin")
@With(Secure.class)
public class Answers extends CRUD {
    
}
