package controllers;

import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
import models.Question;

@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }
 
    public static void index() {
        render();
    }
    
    public static void exportQuestions() {
    	List<Question> q = (List<Question>) Question.all();
    	Question[] questions = new Question[q.size()];
    	for(int i = 0; i < q.size(); i++) {
    		questions[i] = q.get(i);
    	}
    	render(questions);
    }
    
}