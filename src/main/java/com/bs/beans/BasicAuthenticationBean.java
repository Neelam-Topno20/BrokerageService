package com.bs.beans;

public class BasicAuthenticationBean {
private String message;

public BasicAuthenticationBean(String message) {
	super();
	this.message = message;
}

public BasicAuthenticationBean() {
	super();
	// TODO Auto-generated constructor stub
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

@Override
public String toString() {
	return "BasicAuthenticationBean [message=" + message + "]";
}

}
