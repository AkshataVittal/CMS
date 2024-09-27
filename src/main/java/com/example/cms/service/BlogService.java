package com.example.cms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.dto.BlogRequest;
import com.example.cms.dto.BlogResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest);

	ResponseEntity<Boolean> checkTitleAvailability(String tittle);
	
	ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);

	ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(int blogId, BlogRequest blogRequest);

}
