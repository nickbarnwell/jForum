package models;

/**
 * This class is the model definition for the Answer object used throughout
 * the jForum application.
 * 
 * Public fields have setters and getters generated at compile time, and
 * are set in dynamic style of Object.fieldName = newValue throughout
 * the app. This is changed to use Object.setFieldName(newValue) at compile
 * time
 * 
 * @mastery Aspect #7: Inheritance
 */

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
import models.Question;

import utilities.MD5;

@Entity
public class Answer extends Model {

	@Required //Can't be created without a content String set
	@MaxSize(100000) //Maximum of 100k characters
    @Lob //Stored as binary blob in MySQL
    public String content;

    public Date submittedAt;

    @Required
    public int approval;
    
    public Boolean mailed;
    
    @ManyToOne
    public User author;
    
    @ManyToOne
    public Question question;

    public String approvalKey;

    public Answer(Question question, String content, User author) {
        this.author = author;
        this.content = content;
        this.question = question;
        this.submittedAt = new Date();
        this.approval = -1;
        this.generateKey();
    }
    /**
     * As in the Question model, generates the MD5 hash for the answer
     * @throws Exception 
     * @throws NoSuchAlgorithmException 
     */
    public void generateKey() {
        try {
            this.approvalKey = "a" + MD5.genMD5(this.content+" "+this.submittedAt.toString());

        } catch (Exception e) {
        	if(e instanceof play.exceptions.DatabaseException) {
            System.out.println("Generation of key failed due to transient DB failure. Requeued");}
        	else {
        		System.out.println(e);
        	}
        }
    }
    public String toString() {
        return String.format("Answer by %s to Question ID #%d", this.author, this.question.id); 
    }
}
