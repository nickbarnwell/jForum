package models;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {
	
	public String email;
	public String password;
	public String fullname;
	
	public int grade;
	
	public boolean isAdmin;
	
	public User(String email, String fullname, int grade) {
		this.email = email;
		this.fullname = fullname;
		this.grade = grade;
	}
}