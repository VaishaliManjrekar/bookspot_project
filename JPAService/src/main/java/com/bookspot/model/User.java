package com.bookspot.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	
	@Column(nullable =false, unique = true, length = 45)
	 private String email;
	
	@Column(nullable = false, length = 200)
	private String password;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	 
	@Column(name = "lastName", nullable = false)
    private String lastName;
   
	@Column(name = "role", nullable = false)
	private String role;
	
	@Column(name = "token", nullable = true)
	private String token;
  
    public User() {	
    }
      
    public User(int id, String firstName, String lastName, String email, String password, String role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}
