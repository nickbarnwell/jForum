package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Answer extends Model{
	
	@Lob
	public String content;
	
	@ManyToOne
	public User author;
	
	public Date submittedAt;
	public int answerApproval;

	@ManyToOne
	public Question question;
	
	public Answer(Question question, String content, User author) {
		this.author = author;
		this.content = content;
		this.question = question;
		this.submittedAt = new Date();
		this.answerApproval = -1;		
	}
}
