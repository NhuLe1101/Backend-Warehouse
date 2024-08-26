package com.backend.warehouse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
    @Column(nullable = false, length = 30)
    private String username;
	
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, length = 30)
    private String profileName;
    
    @Column(nullable = false, length = 30)
    private String email;
    
	public User() {

	}
	
	public User(String username, String password, String profileName, String email) {
		this.username = username;
		this.password = password;
		this.profileName = profileName;
		this.email = email;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
