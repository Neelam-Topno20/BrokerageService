package com.bs.exception;

public class NoTradesFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTradesFoundException() {
		super();
	}

	public NoTradesFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoTradesFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoTradesFoundException(String message) {
		super(message);
	}

	public NoTradesFoundException(Throwable cause) {
		super(cause);
	}

}
