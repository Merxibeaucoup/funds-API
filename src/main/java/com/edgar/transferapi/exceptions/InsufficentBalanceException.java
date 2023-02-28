package com.edgar.transferapi.exceptions;

public class InsufficentBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public InsufficentBalanceException(String message) {
		super(message);
	}

}
