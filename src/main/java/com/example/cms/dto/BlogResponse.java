package com.example.cms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
	
	private int blogId;
	@NotNull(message="Enter name")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])",message="Enter valid tittle")
	private String tittle;
	@NotNull(message="Enter name")
	private String[] topics;
	private String about;
	
	
	

}
