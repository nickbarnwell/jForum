package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Question extends Model{
	@Lob
	public String question;
	
	public Date submittedAt;
	
	public int questionApproval;
	
	@ManyToOne
	public User submitter;
	@OneToOne
	public User respondent;
	
	public Question(String Question, User submitter, User respondent) {
		this.question = question;
		this.submittedAt = new Date();
		this.questionApproval = -1;
		
		this.submitter = submitter;
		this.respondent = respondent;
		
		
	}
	
}
