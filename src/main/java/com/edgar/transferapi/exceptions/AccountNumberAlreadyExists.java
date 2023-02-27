package com.edgar.transferapi.exceptions;

public class AccountNumberAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public AccountNumberAlreadyExists(String message) {
		super(message);
	}

}
