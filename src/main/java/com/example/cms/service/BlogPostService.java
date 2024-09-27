package com.example.cms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.model.BlogPost;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId, BlogPostRequest blogPostRequest);
	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId,@RequestBody BlogPostRequest blogPostRequest);
	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);
		
}
