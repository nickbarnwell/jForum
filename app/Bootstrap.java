import java.util.List;

import play.*;
import play.jobs.*;
import play.test.*;
import models.*;

@OnApplicationStart
public class Bootstrap extends Job {
	public void doJob() {
		if (play.Play.id == "prod") {
			if (User.find("isAdmin is True").fetch().size() <= 0) {
				Fixtures.load("admin.yml");
			}
		}
		resetMails();
	}

	public void resetMails() {
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
