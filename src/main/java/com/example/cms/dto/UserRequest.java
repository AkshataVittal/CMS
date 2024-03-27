package com.example.cms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	private int userId;
	private String userName;
	private String email;
	private String password;

}
