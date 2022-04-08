package it.smartcommunitylab.challenges.model;

import java.util.ArrayList;
import java.util.List;

public class AuthUser {
	private String username;
	private String password;
	private String role;
	private String email;
	private List<String> domains = new ArrayList<String>();

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	@Override
	public String toString() {
		return "AuthUser [username=" + username + ", role=" + role + ", email=" + email + ", domains=" + domains + "]";
	}
	
	

}
