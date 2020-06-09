package com.bs.customresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MessageResponse {
	private String message;

	@Override
	public String toString() {
		return "MessageResponse [message=" + message + "]";
	}

	public MessageResponse() {
		super();
	}

	public MessageResponse(String message) {
		super();
		this.message = message;
	}
}
