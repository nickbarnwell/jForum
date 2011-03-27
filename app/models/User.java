package models;

/**
 * This class is the model definition for the User object used throughout the
 * jForum application to keep track of who's who.
 *
 * Public fields have setters and getters generated at compile time, and
 * are set in dynamic style of Object.fieldName = newValue throughout
 * the app. This is changed to use Object.setFieldName(newValue) at compile
 * time
 * 
 * @mastery Aspect #7: Inheritance
 */

import java.util.*;
import play.data.validation.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {
	@Required
	@Email //Performs validation before saving
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
	/**
	 * Used by Security controller to authenticate a user.
	 * @param email
	 * @param password
	 * @return
	 */
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
	/**
	 * Used for determining which is a larger value as opposed to
	 * implementing the Comparable interface due to Java's lack of 
	 * multiple inheritance (can't extend AND implement)
	 * 
	 * @param that The other object
	 * @return
	 */
	public int compareTo(Object that) throws ClassCastException {
		if(that instanceof User) {
			User other = (User) that;
			if(other.id == this.id) {
				return 0;
			}
			return (other.id < this.id) ? 1:-1; 
		}
		else {
			throw new ClassCastException();
		}
	}
}