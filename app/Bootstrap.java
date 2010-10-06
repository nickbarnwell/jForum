import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Check if the database is empty
    	System.out.printf("Users: %d\nQuestions: %d\n", User.count(), Question.count());
        Fixtures.deleteAll();
    	Fixtures.load("data.yml");
        }
    }
