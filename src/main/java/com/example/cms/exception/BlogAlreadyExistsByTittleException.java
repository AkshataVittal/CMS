package com.example.cms.exception;

public class BlogAlreadyExistsByTittleException extends RuntimeException {
	
	private String message;

	public BlogAlreadyExistsByTittleException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
