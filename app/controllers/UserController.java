package controllers;

import java.io.File;
import java.util.*;
import play.mvc.*;
import play.*;
import play.data.validation.*;
import play.data.validation.Error;
import utilities.CSV;
import models.*;
import java.util.ArrayList;
import java.util.List;

import models.User;
import notifiers.Mails;
/**
 * This controller handles all of the actions and responses associated
 * with the creation and management of users.
 * @author Nick Barnwell
 * @mastery Polymorphism
 */
public class UserController extends Controller{
	/**
	 * This method is the backend for the automcpleting dialog boxes
	 * on the question submission pages.
	 * 
	 * An AJAX callback is made to the url with the term querystring.
	 * The querystring is first escaped to avoid XSS and SQL injection.
	 * a SQL query is made 'SELECT * FROM USERS WHERE FULLNAME LIKE $TERM'
	 * The returned list is then rendered to a JSON array and returned to
	 * the autocomplete dialog, which then presents it to the user.
	 * 
	 * 
	 * @param term The name of the user
	 * @mastery Searching for specified data
	 */
	public static void getUsers(String term) {
		term += "%"; // For SQL statement like
		List<User> users = User.find("byFullnameLike", term).fetch();
		List<String> userList = new ArrayList<String>(users.size());
		for (User u : users) {
			userList.add(u.fullname);
		}
		if(userList.size() <= 0) userList.add("No user found by that name");
		renderJSON(userList);
	}
	/**
	 * This renders the list of all users, sorted by ID number.
	 */
	public static void userlist() {
		List<User> users = User.all().fetch(); //Fetch all users 
		render();
	}
	/**
	 * This is the POST handler for the User Submission page.
	 * 
	 * The required anotations in the constructor denote that an
	 * exception will be raised if they do not exist in the POST
	 * body. If so, the form is rerendered with the incorrect sections
	 * highlighted.
	 * <p>
	 * If there are not errors, then first a check is made to ensure
	 * that the user registered does not already have their email
	 * in the database. If so, a Flash error is set and the form
	 * is rerendered. Otherwise, a new user is created, the user
	 * is notified of their successful registration, and they are
	 * redirected to the submission page.
	 * 
	 * @param fullname The user's fullname, sent in POST body
	 * @param email User's email
	 * @param grade User's grade
	 */
	public static void addUser(@Required String fullname,
			@Required String email, @Required String grade) {
		if (validation.hasErrors()) {
			render("Application/userForm.html");
		}
		try {
			if (User.find("byEmail", email).first() != null) {
				System.out.println(User.find("byEmail", email).first());
				flash.error("That user has already been registered, please try again");
				userForm();
			} else {

				User u = new User(email, fullname, Integer.parseInt(grade))
						.save();
				flash.success("You've been added as a user, now go ahead and submit a question");
				Mails.notifyRegistration(u);
				Application.submitForm();
			}

		} catch (Exception e) {
			flash.error("An error has occurred, please try again.");
			System.out.println(e);
			userForm();
		}
	}
	/**
	 * Renders the UserForm
	 */
	public static void userForm() {
		render();
	}
	
}
