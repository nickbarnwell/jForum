import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTests extends UnitTest {
	@Before
	public void setup() {
		Fixtures.deleteAll();
	}
	
	
    @Test
    public void createAndRetrieveUser() {
        new User("nick.barnwell@gmail.com", "Nick Barnwell", 12).save();
        
        User nick = User.find("byEmail", "nick.barnwell@gmail.com").first();
        
        assertNotNull(nick);
        assertEquals("Nick Barnwell", nick.fullname);
        assertEquals(12, nick.grade);
    }
    
    @Test 
    public void createQuestion() {
        User nick = new User("nick.barnwell@gmail.com", "Nick Barnwell", 12).save();
        User ds = new User("dsarstedt@cisdk.dk", "Daniel Sarstedt", 13).save();
        
        new Question("Hey Mr. S, this is a unit testing question!", nick, ds).save();
        
        assertEquals(1, Question.count());
        
        List<Question> nickPosts = Question.find("bySubmitter", nick).fetch();
    	
    }

}
