package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Question extends Model{
	@Lob
	public String content;
	
	public Date submittedAt;
	
	public int questionApproval;
	
	@ManyToOne
	public User author;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	public List<Answer> answers;
	
	public Question(String content, User author) {
		this.answers = new ArrayList<Answer>();
		this.content = content;
		this.submittedAt = new Date();
		this.questionApproval = -1;
		this.author = author;
	}
	

	public Question addAnswer(User author, String content) {
		Answer newAnswer = new Answer(this, content, author).save();
		this.answers.add(newAnswer);
		this.save();
		return this;
	}
	
}
