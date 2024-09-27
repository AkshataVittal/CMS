package com.example.cms.dto;

import com.example.cms.enums.PostType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogPostRequest {

	@NotNull(message = "Enter should not be null")
	private String title;
	private String subTitle;
	@Min(value = 500,message = "Enter atleast 500 charracters")
	@Max(value = 1000,message = "enter maximum of 400 characters only")
	private String summary;
	@Enumerated(EnumType.STRING)
	private PostType postType;
}
