package notifiers;

import models.*;

import play.*;
import play.mvc.*;
import java.util.*;

public class Mails extends Mailer{
    public static void notifyQuestion(Question question) {
        setFrom("Nick Barnwell <nick.barnwell@gmail.com>");
        setReplyTo("nick.barnwell@gmail.com");
        setSubject("%s, you've been asked a question!", question.receiver);
        addRecipient(question.receiver.email);
        send(question);   
    }
    
    public static void approveQuestion(Question question) {
        setFrom("Nick Barnwell <nick.barnwell@gmail.com>");
        setReplyTo("nick.barnwell@gmail.com");
        setSubject("%s, you need to approve your Forum question!", question.author, question.receiver);
        addRecipient(question.author.email);
        send(question);   
    }
    
    public static void approveAnswer(Answer answer) {
        setFrom("Nick Barnwell <nick.barnwell@gmail.com>");
        setReplyTo("nick.barnwell@gmail.com");
        setSubject("%s, you need to approve your Forum answer!", answer.author );
        addRecipient(answer.author.email);
        send(answer);   
    }
    
    public static void notifyRegistration(User user) {
    	setFrom("Nick Barnwell <nick.barnwell@gmail.com>");
        setReplyTo("nick.barnwell@gmail.com");
        
        List<User> admins = User.find("isAdmin is true").fetch();
        System.out.println("Is this thing on?");
		for(User u:admins) { System.out.println(u.fullname); addRecipient(u.email);}
		setSubject("A new user has registered for forum (Hallaleujah)!");
        send(user);
        
    }
}