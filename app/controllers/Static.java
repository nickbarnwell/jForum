package controllers;

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
