package com.example.cms.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.cms.model.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {
	
	@NotNull(message="Enter name")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])",message="Enter valid tittle")
	private String tittle;
	
	private String[] topics;
	private String about;
	
	private List<User> userList=new ArrayList<>();

}
