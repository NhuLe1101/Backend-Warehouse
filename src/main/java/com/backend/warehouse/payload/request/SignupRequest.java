package com.backend.warehouse.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	@NotBlank
	@Size(min = 6, max = 30)
	private String profileName;

	@NotBlank
	@Size(min = 6, max = 40)
	private String email;
	
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