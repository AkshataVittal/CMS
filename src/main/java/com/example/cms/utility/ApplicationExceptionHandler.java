package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserAlreadyExisistingByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
//@AllArgsConstructor
public class ApplicationExceptionHandler {

	private ErrorStructure<String> structure;
	
	public ApplicationExceptionHandler(ErrorStructure<String> structure) {
		super();
		this.structure = structure;
	}
	
	private ResponseEntity<ErrorStructure<String>> erroresponse(
			HttpStatus status,String message,String rootCause){

		return new ResponseEntity<ErrorStructure<String>>(structure
				.setStatus(status.value())
				.setMessage(message)
				.setRootCause(rootCause),status);
	}
	@ExceptionHandler	
	public ResponseEntity<ErrorStructure<String>> userAlreadyExisistByEmail(
			UserAlreadyExisistingByEmailException ex) {

		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage() ,"User already exist wth given email" );

	}
	@ExceptionHandler	
	public ResponseEntity<ErrorStructure<String>> userNotFoundById(
			UserNotFoundByIdException ex) {

		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage() ,"User not found by Id" );

	}
}
