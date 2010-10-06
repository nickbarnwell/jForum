package controllers;

import java.util.*;

import play.mvc.*;
import play.*;
import models.*;

public class Application extends Controller {

    public static void index() {
        Question latestQuestion = Question.find("order by submittedAt desc").first();
    	render(latestQuestion);
    }

}