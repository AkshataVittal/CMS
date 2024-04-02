package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.BlogAlreadyExistsByTittleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.ContributionPanelNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UnAuthorizedException;
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
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String >> panelNotFoundById(
			PanelNotFoundByIdException ex) {
		return erroresponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"panel id not found");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> IllegalAccessRequest(
			IllegalAccessRequestException ex){
		return erroresponse(HttpStatus.BAD_GATEWAY,ex.getMessage(),"IllegalAccessRequest");
		
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> UnAuthorized(
			UnAuthorizedException ex){
		return erroresponse(HttpStatus.BAD_GATEWAY,ex.getMessage(),"Un authorized access");
		
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> BlogPostNotFoundById(
			BlogPostNotFoundByIdException ex){
		return erroresponse(HttpStatus.BAD_GATEWAY,ex.getMessage(),"Blog post Id not found");
		
	}
	

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> ContributionPanelNotFoundById(
			ContributionPanelNotFoundByIdException ex){
		return erroresponse(HttpStatus.BAD_GATEWAY,ex.getMessage(),"Contribution panel id not found");
		
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> TitleAlreadyExists(
			TitleAlreadyExistsException ex){
		return erroresponse(HttpStatus.BAD_GATEWAY,ex.getMessage(),"Tittle already exist");
		
	}
	
	
}
