package controllers;

/**
 * This controller exists to render the Static pages for the public
 * part of the site.
 *  
 * @author Nick Barnwell
 */

import java.util.*;
import play.mvc.*;
import play.*;
import play.data.validation.*;
import play.data.validation.Error;
import models.*;

public class Static extends Controller{
	public static void index() {
		render();
	}
	public static void tutorial() {
		render();
	}
}
