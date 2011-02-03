package models;

import java.util.*;
import javax.persistence.*;
import play.data.validation.*;

import play.db.jpa.*;
import utilities.MD5;

@Entity
public class Question extends Model{
	@Required
	@MaxSize(100000)
    @Lob
	public String content;
	
	public Date submittedAt;

	public int approval;
	public String approvalKey;
	public Integer wordcount;
	
	@ManyToOne
	public User author;
	@ManyToOne
	public User receiver;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	public List<Answer> answers;
	
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
	
	private void wordCount() {
	    this.wordcount = this.content.split("[\\s^\\*]+").length; //Regex to match
    }
	
	public void approve(String approvalKey) {
	    this.approval = (approvalKey == this.approvalKey) ? 1 : 0;	    
	    this.save();
	}
	
	private void generateKey() {
	    try {
	        this.approvalKey = ("q" + MD5.genMD5(this.content));
	        }
	    catch (Exception e) {
	        System.out.println("Generation of key failed");
	    }
	}
	

	
	public Answer addAnswer(User author, String content) {
		Answer newAnswer = new Answer(this, content, author).save();
		this.answers.add(newAnswer);
		this.save();
		return newAnswer;
	}
	
	
}
