package com.bid90.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity()
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	String email;
	
	String name;
	
	String password;

	String userRole;
	
	Integer attemptPassword;
		
	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getAttemptPassword() {
		return attemptPassword;
	}

	public void setAttemptPassword(Integer attemptPassword) {
		this.attemptPassword = attemptPassword;
	}

	public void addAttemptPassword() {
		this.attemptPassword += 1;
	}
	
}
