package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.exception.UserAlreadyExisistingByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepo;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;
	private ResponseStructure<UserResponse> structure;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepo userRepo, ResponseStructure<UserResponse> structure,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.structure = structure;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest)
	{
		if(userRepo.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExisistingByEmailException("Failed to register user");

		User user=userRepo.save(mapToUserEntity(userRequest,new User()));

		return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
				.setMessage("user registered successfully")
				.setBody(mapToUserResponse(user)));	
	}

	private UserResponse mapToUserResponse(User user) {


		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.createdAt(user.getCreatedAt())
				.lastModifiedAt(user.getLastModifiedAt())
				.build();		
	}

	private User mapToUserEntity(UserRequest userRequest,User user) {

		user.setUserName(userRequest.getUserName());
		user.setEmail(userRequest.getEmail());
		//user.setPassword(userRequest.getPassword());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setDeleted(false);
		return  user; 
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		return userRepo.findById(userId).map(user->{
			if(!user.isDeleted())
				return ResponseEntity.ok(structure.setMessage("Found success")
						.setStatus(HttpStatus.OK.value())
						.setBody(mapToUserResponse(user)));
			throw new UserNotFoundByIdException("user Id already deleted");
		})
				.orElseThrow(()->new UserNotFoundByIdException("User Not found By Id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUserById(int userId) {
		return userRepo.findById(userId).map(user->{
			user.setDeleted(true);
			userRepo.save(user);
			return ResponseEntity.ok(structure
					.setMessage("user deleted success")
					.setStatus(HttpStatus.OK.value())
					.setBody(mapToUserResponse(user)));
		}).orElseThrow(()->new UserNotFoundByIdException("User Id not found"));
	}
}

