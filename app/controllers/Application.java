package controllers;

import java.util.*;

import play.mvc.*;
import play.*;
import play.data.validation.*;
import play.data.validation.Error;
import models.*;
import notifiers.*;
import utilities.*;


public class Application extends Controller {

	public static void question(Long id) {
		Question question = Question.findById(id);
		render(question);
	}

	public static void questionStats() {
		Map<User, Integer> results = new HashMap<User, Integer>();
		List<User> users = User.all().fetch(); //Fetch all users
		for (User u : users) {
			results.get(u);
			List<Question> questions = Question.find("byAuthor", u).fetch();
			for (Question q : questions) {
				Integer count = results.get(u);
				results.put(u, (count == null) ? q.wordcount : count
						+ q.wordcount); //Ternary operator to avoid NPEs
			}
		}
		BTree<Pair<User, Integer>> tree = new BTree();
		for(User u: users) {
			Pair<User, Integer> pair = new Pair(u, results.get(u));
			tree.insert(pair);
		}
		ArrayList<Pair<User, Integer>> res = tree.sortInOrder();
		
		render("Application/stats.html", res);

	}

	public static void approve(String key) {
		if (key.substring(0, 1).equals("q")) {
			try {
				Question question = Question.find("byApprovalKey", key).first();
				if (question.approval != 1) {
					question.approval = 1;
					question.save();
					flash.success("Your question has been approved");
					// Mails.notifyQuestion(question); To be reenabled at a
					// alter date
					question(question.id);
				} else {
					question(question.id);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (key.substring(0, 1).equals("a")) {
			try {
				Answer answer = Answer.find("byApprovalKey", key).first();
				if (answer.approval != 1) {
					answer.approval = 1;
					answer.save();
					question(answer.question.id);
				} else {
					question(answer.question.id);
				}
			} catch (Exception e) {
				System.out.println("No such amswer");
			}
		}
	}
	
	public static void submitForm() {
		render("Application/submit.html");
	}
	
	public static void submit(
			@Required(message = "Your name should be here.") String author,
			@Required(message = "You need to enter a recipient's name!") String receiver,
			@Required(message = "A question is required") String content) {
		if (validation.hasErrors()) {
			submitForm();
		}
		User uAuthor = User.find("byFullname", author).first();
		User uReceiver = User.find("byFullname", receiver).first();
		if (uAuthor == (null) || uReceiver == (null)) {
			flash.error("User not found, please try again");
			render("Application/submit.html");
		} else {
			Question submission = new Question(content, uAuthor, uReceiver)
					.save();
			flash.success("Thanks for posting %s", submission.author.fullname);
			Mails.approveQuestion(submission);
			question(submission.id);
		}
	}
//Implement updated answer in email template
	public static void answerQuestion(Long questionID, String content) {
		Question question = Question.findById(questionID);
		Answer answer = question.addAnswer(question.receiver, content);
		Mails.approveAnswer(answer);
		question(questionID);

	}

	public static void export() {
		List<Question> q = Question.findAll();
		Question[] ques = new Question[q.size()];
		for (int i = 0; i < q.size(); i++) {
			ques[i] = q.get(i);
		}
//		utilities.CSV csv = new CSV();
	}
}