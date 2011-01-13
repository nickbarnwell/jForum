package controllers;

import java.util.*;

import play.mvc.*;
import play.*;
import play.data.validation.*;
import models.*;
import notifiers.*;

public class Application extends Controller {
    
	public static void questionEmail(Long id) {
        Question question = Question.findById(id);
        render(question);
	}
	
    public static void getUsers(String term) {
//        if play.mode == "ibevaluation" {
//        		User[] usersList = new User[30];
//        		
//        		users = User.find
//        }
    	//System.out.println(term); //For debugging terms
        term += "%"; //For SQL statement like
        List<User> users = User.find("byFullnameLike", term).fetch();
        List<String> userList = new ArrayList<String>(users.size());
        for(User u : users) {
            userList.add(u.fullname);
        }
        renderJSON(userList);
    }
    
    public static void listUsers() {
    	
    }
    
    public static void index() {
        render();
    }
    
    public static void spage(String page) {
    	render("static/"+page+".html");
    }

    public static void question(Long id) {
        Question question = Question.findById(id);
        render(question);
    }

//    public static void questionStats() {
//        Map<User, Integer> results = new HashMap<User, Integer>();
//        List<User> users = User.find().fetch();
//        for (User u : users) {
//            results.get(u);
//            
//            List<Question> questions = Question.findBy("byAuthor", u);
//            for (Question q : questions) {
//                Integer count = results.get(u);
//                results.put(u, (count == null) ? q.wordcount : count + q.wordcount);
//                System.out.println(results.get(u));
//            }
//        }
//        users = Collections.sort(users, user.fullname);
//        render("Application/stats.html", results, users);
//
//    }

    public static void approve(String key) {
        if (key.substring(0, 1).equals("q")) {
            try {
                Question question = Question.find("byApprovalKey", key).first();
                if (question.approval != 1) {
                    question.approval = 1;
                    question.save();
                    flash.success("Your question has been approved");
//                    Mails.notifyQuestion(question); To be reenabled at a alter date
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

    public static void submit(
            @Required(message = "Your name should be here.") String author,
            @Required(message = "You need to enter a recipient's name!") String receiver,
            @Required(message = "A question is required") String content) {
        if (validation.hasErrors()) {
            render("Application/submitForm.html");
        }
        try {
            User uAuthor = User.find("byFullname", author).first();
            User uReceiver = User.find("byFullname", receiver).first();
            if (uAuthor.equals(null) || uReceiver.equals(null)) {
                flash.error("User not found, please try again");
                submitForm();
            } else {
                Question submission = new Question(content, uAuthor, uReceiver).save();
                flash.success("Thanks for posting %s", submission.author.fullname);
                Mails.approveQuestion(submission);
                question(submission.id);
            }
        } catch (Exception e) {
            flash.error("An error occurred, please try again");
            submitForm();
        }        
    }
    
    public static void submitForm() {
        render();
    }
    
    public static void addUser(@Required String fullname, @Required String email, @Required String grade) {
        if (validation.hasErrors()) {
            render("Application/userForm.html");
        }
        try {
            if(User.find("byEmail", email).first() != null) {
                System.out.println(User.find("byEmail", email).first());
                flash.error("That user has already been registered, please try again");
                userForm();
            }
            else {
                System.out.print(Integer.parseInt(grade));
                User u = new User(email, fullname, Integer.parseInt(grade)).save();
                flash.success("You've been added as a user, now go ahead and submit a question");
                submitForm();
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
    

    public static void answerQuestion(Long questionID, String content) {
        Question question = Question.findById(questionID);
        Answer answer = question.addAnswer(question.receiver, content);
        Mails.approveAnswer(answer);
        question(questionID);
        
    }
}