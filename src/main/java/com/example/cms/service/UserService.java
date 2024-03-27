package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {
	
	 ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> softDeleteUserById(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);

}
