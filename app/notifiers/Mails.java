package notifiers;

import models.*;

import play.*;
import play.mvc.*;
import java.util.*;

public class Mails extends Mailer{
	private final static String from = "Labyrinth Staff <labyrinth@boltoncomputing.com>";
	private final static String replyTo = "labyrinth@boltoncomputing.com";
    public static void notifyQuestion(Question question) {
        setFrom(from);
        setReplyTo(replyTo);
        setSubject("%s, you've been asked a question!", question.receiver);
        addRecipient(question.receiver.email);
        send(question);   
    }
    
    public static void approveQuestion(Question question) {
        setFrom(from);
        setReplyTo(replyTo);
        setSubject("%s, you need to approve your Forum question!", question.author, question.receiver);
        addRecipient(question.author.email);
        send(question);   
    }
    
    public static void approveAnswer(Answer answer) {
        setFrom(from);
        setReplyTo(replyTo);
        setSubject("%s, you need to approve your Forum answer!", answer.author );
        addRecipient(answer.author.email);
        send(answer);   
    }
    
    public static void notifyRegistration(User user) {
    	setFrom(from);
        setReplyTo(replyTo);
        
        List<User> admins = User.find("isAdmin is true").fetch();
        System.out.println("Is this thing on?");
		for(User u:admins) { System.out.println(u.fullname); addRecipient(u.email);}
		setSubject("A new user has registered for forum (Hallaleujah)!");
        send(user);
        
    }
}