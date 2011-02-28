import java.util.List;

import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
    	System.out.print(User.find("isAdmin is True").fetch().size());
    	if(User.find("isAdmin is True").fetch().size()==0) {
    		Fixtures.load("admin.yml");;
    	}
    } 
}
