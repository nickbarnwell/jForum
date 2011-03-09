package controllers;

import play.*;
import play.mvc.*;
import play.mvc.results.RenderTemplate;
import utilities.CSV;
 
import java.io.File;
import java.util.*;
 
import models.*;

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
	
	public static void submitCSV() {
		render();
	}
	
	public static void importCSV(File file) {
		CSV csv = new CSV(file, true, ",");
		ArrayList<ArrayList<String>> data = csv.getData();
		ArrayList<String> header = csv.getHeader();
		for(ArrayList<String> row:data) {
			if(User.find("byEmail", row.get(3)).first() == null) {
				User u = new User(row.get(3), row.get(0)+" "+row.get(1), Integer.parseInt(row.get(2).replaceAll("\\D+", ""))).save();
			}
			
		}
		flash.success("Users successfully created");
		render(data, header);
	}
	
//	public static void importCSV(String title, File file) {
//		CSV csv = new CSV(file, true, ",");
//		//csv.print();
//		ArrayList<ArrayList<String>> data = csv.getData(); 
//		renderTemplate("admin/confirmCSV.html", data);
//	}
    
}