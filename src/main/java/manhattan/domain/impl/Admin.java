package manhattan.domain.impl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import manhattan.domain.AbstractEntity;
@Entity
@AttributeOverride(name = "id", column = @Column(name = "ADMIN_ID"))
public class Admin extends AbstractEntity<Admin> {
	
	private static final long serialVersionUID = -7675205724712859227L;
	
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	
	@Column(name = "EMAIL", length = 100, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PASSWORD", length = 100, nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "FIRST_NAME", length = 50, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME", length = 50, nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "Admin [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}
	
	@Override
	public int compareTo(Admin o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
