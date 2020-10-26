package com.bs.customresponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
	private HttpStatus status;
	private String message;
	private HashMap<String,String> error;

	public ApiError(HttpStatus status, String message, HashMap<String,String> error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}

	/*
	 * public ApiError(HttpStatus status, String message, String error) { super();
	 * this.status = status; this.message = message; errors = Arrays.asList(error);
	 * }
	 */
}
