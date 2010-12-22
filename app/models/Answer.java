package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

import utilities.MD5;

@Entity
public class Answer extends Model {

    @Required
    @Lob
    @MaxSize(100000)
    public String content;

    @ManyToOne
    public User author;

    public Date submittedAt;

    @Required
    public int approval;

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
    private void generateKey() {
        try {
            this.approvalKey = "a" + MD5.genMD5(this.content);

        } catch (Exception e) {
            System.out.println("Generation of key failed");
        }
    }
    public String toString() {
        return String.format("Answer by %s to Question ID #%d", this.author, this.question.id); 
    }
}
