package com.edgar.transferapi.exceptions;

public class AccountDoesntExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountDoesntExistException(String message) {
		super(message);
	}

}
