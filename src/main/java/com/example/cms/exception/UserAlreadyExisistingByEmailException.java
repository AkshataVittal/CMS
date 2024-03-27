package com.example.cms.exception;


public class UserAlreadyExisistingByEmailException extends RuntimeException{
	
	private String message;

	public UserAlreadyExisistingByEmailException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	

}
