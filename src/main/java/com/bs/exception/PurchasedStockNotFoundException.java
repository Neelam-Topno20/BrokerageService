package com.bs.exception;

import java.util.function.Supplier;

public class PurchasedStockNotFoundException extends RuntimeException  {

	public PurchasedStockNotFoundException() {
		super();
	}

	public PurchasedStockNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PurchasedStockNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PurchasedStockNotFoundException(String message) {
		super(message);
	}

	public PurchasedStockNotFoundException(Throwable cause) {
		super(cause);
	}

	

}
