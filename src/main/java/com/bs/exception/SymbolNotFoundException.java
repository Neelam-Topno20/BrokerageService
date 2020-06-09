package com.bs.exception;

public class SymbolNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SymbolNotFoundException() {
		super();
	}

	public SymbolNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SymbolNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SymbolNotFoundException(String message) {
		super(message);
	}

	public SymbolNotFoundException(Throwable cause) {
		super(cause);
	}

}
