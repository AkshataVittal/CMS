package com.example.cms.exception;

public class PanelNotFoundByIdException extends RuntimeException{
	
	private String message;
	
	PanelNotFoundByIdException(String messagre){
		this.message=messagre;
	}
	
	public String getMessage() {
		return message;
	}
	
	

}
