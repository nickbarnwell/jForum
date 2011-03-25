import java.util.List;

import play.*;
import play.jobs.*;
import play.test.*;
import models.*;
/**
 * The Bootstrap controller lists all of the jobs that are to be
 * run by the application's host process. Some occur only on
 * application start, while others might be set to run once 
 * an hour. They are similar in function to cronjobs.
 * 
 * @author Nick Barnwell
 */
@OnApplicationStart //Runs the below tasks on app start
public class Bootstrap extends Job {
	public void doJobs() {
		if (play.Play.id == "prod") { //If running in prod mode
			//Checks to see whether any admins exist
			if (User.find("isAdmin is True").fetch().size() <= 0) {
				//if not, creates a default admin from yml
				Fixtures.load("admin.yml");
			}
		}
		resetMails();
	}
	/**
	 * During a schema migration a mailed cateogry was added to questions
	 * to keep track of whether the user had been notified of their question
	 * yet or not. This meant models created before its addition to the schema
	 * had a null field rather than a boolean. This job fixes that by iterating
	 * through all of the questions & answers and setting it to false if mailed
	 * is null. 
	 */
	private void resetMails() {
		List<Question> qs = Question.findAll();
		for (Question q : qs) {
			if (q.mailed == null) {
				q.mailed = false;
				q.save();

			}
		}
		List<Answer> as = Answer.findAll();
		for (Answer a : as) {
			if (a.mailed == null) {
				a.mailed = false;
				a.save();
			}
		}
	}
}
