package controllers;

import play.*;
import play.mvc.*;
import play.mvc.results.RenderTemplate;
import utilities.CSV;

import java.io.File;
import java.util.*;

import models.*;

/**This class implements all of the controllers for the admin interface
 * excluding those related to the CRUD portion.
 * 
 * This implements the With annotation to demarcate it as a class
 * that implements the Secure interface for user authentication
 * Any view in the admin controller will first check if the user
 * is logged in and if not redirects them with a 302 to the login
 * screen
 */
@With(Secure.class)
public class Admin extends Controller {
	/** 
	 * This function sets the name of the logged in user and places it
	 * into the session variables.
     */
	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user.fullname);
		}
	}
	/** 
	 * Renders the index template for the admin page
	 */
	public static void index() {
		render();
	}

	public static void exportQuestions() {
		List<Question> q = Question.findAll();

		render(q);
	}
	/**
	 * Renders the CSV submission page
	 */
	public static void submitCSV() {
		render();
	}
	/** This is the POST handler for the CSV import of the admin
	 * interface.
	 * 
	 * A new instance of the CSV class is initalized with the ','
	 * as a seperator, and Header as true.
	 * Then, an array of the CSV data is retrieved and stored to the
	 * data array, as is the header.
	 * FInally, it loops through each row in the data array and searches
	 * for a user with that email address. If they do not already exist
	 * they are created using the data stored in each row.
	 * 
	 * If an error occurs the user is redirected to the submission form
	 * and the transaction is automatically rolled back by the ORM.
	 * 
	 @param file CSV file, posted as a binary and dispatched to the form
	 * 
	 */
	public static void importCSV(File file) {
		CSV csv = new CSV(file, true, ",");
		ArrayList<ArrayList<String>> data = csv.getData();
		ArrayList<String> header = csv.getHeader();
		try {
		for (ArrayList<String> row : data) {
			if (User.find("byEmail", row.get(3)).first() == null) {
				User u = new User(row.get(3), row.get(0) + " " + row.get(1),
						Integer.parseInt(row.get(2).replaceAll("\\D+", "")))
						.save();
			}

		}
		}
		catch (Exception e) {
			flash.error("An error occurred, sorry. Please contact us" +
					"at labyrinth@boltoncomputing.com and we'll try and" +
					"resolve it");
			submitCSV();
		}
		flash.success("Users successfully created, view them below");
		render(data, header);
	}

}