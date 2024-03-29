package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.BlogAlreadyExistsByTittleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserAlreadyExisistingByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

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
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> BlogAlreadyExistsByTittle(
			BlogAlreadyExistsByTittleException ex) {

		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage() ,"Blog already exisist by this tittle" );

	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> BlogNotFoundById(
			BlogNotFoundByIdException ex) {

		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage() ,"Blog id not exsist " );

	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> TopicNotSpecified(
			TopicNotSpecifiedException ex) {

		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage() ,"failed to create blog " );

	}
	
	
}
