package com.ace.mvc.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int id;
	private String name;
	private String password;
	private String email;
	private String role;
	@Override
	public String toString() {
		return "User [password=" + password + "]";
	}

//	public enum Role {
//		admin, user
//	}

	
	
}
