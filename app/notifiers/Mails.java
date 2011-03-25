package notifiers;

import models.*;

import play.*;
import play.mvc.*;
import java.util.*;
/**
 * @author Nick Barnwell
 *
 * This class is the controller for the Mail notifiers used in the JForum
 * application. Each method maps to a similarly named template that may be
 * called from other controllers to send a transactional message.
 * 
 * All of the methods are fairly standard and set the proper fields before
 * calling the template under views to do the actual rendering of the
 * information. The only significant differentiation between this and the
 * regular controllers it that it will also include a text version of the
 * template if provided. For brevity's sake, these are no included in the C1
 * listing as they are merely simpler versions of the HTML templates.
 * 
 */
public class Mails extends Mailer{
	//The From address
	private final static String from = "Labyrinth Staff <labyrinth@boltoncomputing.com>";
	//The reply-to address
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