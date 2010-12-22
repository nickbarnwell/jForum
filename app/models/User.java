package models;
import java.util.*;
import play.data.validation.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {
	@Required
	@Email
	public String email;
	
	public String password;
	
	@Required
	public String fullname;
	@Required
	public int grade;
	
	public boolean isAdmin;
	
	public User(String email, String fullname, int grade) {
		this.email = email;
		this.fullname = fullname;
		this.grade = grade;
	}
	
	public String toString() {
	    return this.fullname;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
}