package com.example.cms.dto;

import java.util.List;

import com.example.cms.model.Blog;
import com.example.cms.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	private int userId;
	private String userName;
	private String email;
	private String password;
	
	private List<User> userList;

}
