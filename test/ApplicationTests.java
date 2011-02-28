import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class ApplicationTests extends UnitTest {
	@Before
	public void setup() {
	    Fixtures.deleteAll();
		Fixtures.load("initial-data.yml");
	}
	
	
    @Test
    public void createAndRetrieveUser() {
        User nick = User.find("byEmail", "nick.barnwell@gmail.com").first();    
        assertNotNull(nick);
        assertEquals("Nick Barnwell", nick.fullname);
        assertEquals(12, nick.grade);
    }
    
    @Test 
    public void createQuestion() {
        User ds = User.find("byFullname", "Daniel Sarstedt").first();
        User nick = User.find("byFullname", "Nick Barnwell").first();
        Question question = new Question("Hey Mr. S, this is a unit testing question!", nick, ds).save();
        assertEquals(question, Question.find("byContent", question.content).first());
    }
    @Test
    public void validateQuestion( ) {
        User nick = new User("nick.barnwell@gmail.com", "Nick Barnwell", 12).save();
        User ds = new User("dsarstedt@cisdk.dk", "Daniel Sarstedt", 13).save();
        new Question("Hey Mr. S, this is a unit testing question!", nick, ds).save();

        List<Question> nickPosts = Question.find("byAuthor", nick).fetch();

        Question question = (Question) nickPosts.get(0);
//        question.generateKey();
        question.checkKey(question.approvalKey);
        
        assertEquals(1, question.approval);
    }
    
    @Test
    public void answerQuestion() {
    	
    }

}
