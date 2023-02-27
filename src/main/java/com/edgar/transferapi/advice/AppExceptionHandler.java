package com.edgar.transferapi.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edgar.transferapi.exceptions.AccountNumberAlreadyExists;



@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AccountNumberAlreadyExists.class)
	public ResponseEntity<Object> handleContactNotFound(AccountNumberAlreadyExists ex, WebRequest request) {

		return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now()),
				HttpStatus.CONFLICT);
	}


}

