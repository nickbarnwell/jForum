package controllers;
/**
 * This controller implements functionality for the CRUD part of the
 * administrative interface. Methods inherit from the toplevel CRUD module
 * and it exists mainly as a skeleton so that it can be added to the list
 * of modules checked by the security controller.
 */
import controllers.CRUD;
import controllers.Check;
import controllers.Secure;
import play.*;
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class Answers extends CRUD {
}
