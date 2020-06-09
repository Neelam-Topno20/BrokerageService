package com.bs.customresponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

	private int statusCode;
	private String message;

	@Override
	public String toString() {
		return "CustomResponse [statusCode=" + statusCode + ", message=" + message + "]";
	}

	public ErrorResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public ErrorResponse() {
		super();
	}

}
