package com.bs.exception;

public class TradeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeNotFoundException() {
		super();
	}

	public TradeNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TradeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TradeNotFoundException(String message) {
		super(message);
	}

	public TradeNotFoundException(Throwable cause) {
		super(cause);
	}

}
