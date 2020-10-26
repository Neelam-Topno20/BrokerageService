package com.bs.beans;

import lombok.Getter;

@Getter
public class AuthenticationResponse {

	private final String jwt;
	
	private final String userId;
	
	private final String role;


	public AuthenticationResponse(String jwt, String userId,String role) {
		super();
		this.jwt = jwt;
		this.userId = userId;
		this.role=role;
	}
	
	
}
