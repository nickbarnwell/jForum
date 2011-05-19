package models;
/**
 * This class is the model definition for the Question object used throughout
 * the jForum application
 * 
 * Public fields have setters and getters generated at compile time, and
 * are set in dynamic style of Object.fieldName = newValue throughout
 * the app. This is changed to use Object.setFieldName(newValue) at compile
 * time
 */
import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
import utilities.MD5;

@Entity //Marks it as a JPASupportable entity
/**
 * As with all of the models in the application, it extends from the base
 * play.db.jpa.Model class, which holds all of the annotations, methods for
 * searching, and so on. For the most part, the only ones that are overridden
 * are toString() and compareTo().
 * 
 * @mastery Aspect #7: Inheritance
 */
public class Question extends Model{
	@Required //Can't be created without a content String set
	@MaxSize(100000) //Maximum of 100k characters
    @Lob //Stored as binary blob in MySQL
	public String content;
	
	public Date submittedAt;

	public int approval;
	public String approvalKey;
	public Integer wordcount;
	
	public Boolean mailed;
	
	@ManyToOne //ManyToOne relationship in DB; auto-creates index for queries
	public User author;
	@ManyToOne
	public User receiver;
	//Maps to answers: cascade means when question deleted, all correlated
	//are deleted
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL) 
	public List<Answer> answers;
	/**
	 * Constructor for the question
	 * @param content The question body
	 * @param author The User object of the author
	 * @param receiver The User object of the receiver
	 */
	public Question(String content, User author, User receiver) {
		this.answers = new ArrayList<Answer>();
		this.content = content;
		this.submittedAt = new Date();
		this.approval = -1;
		this.author = author;
		this.receiver = receiver;
		this.wordCount();
		this.generateKey();	
	}
	
	public String toString() {
	    return this.content.substring(0);
	}
	/**
	 * Ues a regular expression to determine wordcount. Splits on
	 * all punctuation, whitespace that isn't hyphens, and ignores
	 * apostrophes. Total length of resultant array is thus the 
	 * corresponding wordcount. Tested to be ~95% accurate compared
	 * to MSWord count  
	 */
	private void wordCount() {
	    this.wordcount = this.content.split("[\\s^\\*]+").length; //Regex to match
    }
	/**
	 * Public method used to check if approval key is correct
	 * @param approvalKey
	 */
	public void checkKey(String approvalKey) {
	    this.approval = (approvalKey == this.approvalKey) ? 1 : 0;	    
	    this.save();
	}
	/**
	 * Private method used to generate the key; function polymorphism
	 */
	public void generateKey() {
	    try {
	        this.approvalKey = ("q" + MD5.genMD5(this.content+" "+this.submittedAt.toString()));
	        }
	    catch (Exception e) {
	        System.out.println("Generation of key failed");
	    }
	}
	/**
	 * Adds an answer to the question. Creates it, adds to list
	 * and then saves the question.
	 * 
	 * @param author
	 * @param content
	 * @return
	 */
	public Answer addAnswer(User author, String content) {
		Answer newAnswer = new Answer(this, content, author).save();
		this.answers.add(newAnswer);
		this.save();
		return newAnswer;
	}
	
	
}
