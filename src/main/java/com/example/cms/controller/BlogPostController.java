package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.model.BlogPost;
import com.example.cms.service.BlogPostService;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class BlogPostController {
	
	private BlogPostService blogPostService;

	public BlogPostController(BlogPostService blogPostService) {
		super();
		this.blogPostService = blogPostService;
	}

	@PostMapping(name = " /blogs/{blogId}/blog-posts")
	ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(@PathVariable int blogId,@RequestBody BlogPostRequest blogPostRequest){
		return blogPostService.createPost(blogId,blogPostRequest);
	}
	
	@PutMapping(name=" /blog-posts/{postId}")
	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId,@RequestBody BlogPostRequest blogPostRequest){
		return blogPostService.createPost(postId,blogPostRequest);
	}
	@DeleteMapping(name = " /blog-posts/{postId}")
	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	}
	

}
