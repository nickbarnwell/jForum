package controllers;

import java.util.*;
import play.mvc.*;
import play.*;
import play.data.validation.*;
import play.data.validation.Error;
import models.*;
import java.util.ArrayList;
import java.util.List;

import models.User;
import notifiers.Mails;

public class UserController extends Controller{
	
	public static void getUsers(String term) {
		term += "%"; // For SQL statement like
		List<User> users = User.find("byFullnameLike", term).fetch();
		List<String> userList = new ArrayList<String>(users.size());
		for (User u : users) {
			userList.add(u.fullname);
		}
		renderJSON(userList);
	}

	public static void userlist() {
		List<User> users = User.all().fetch(); //Fetch all users
		render();
	}
	
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

	public static void userForm() {
		render();
	}

}
