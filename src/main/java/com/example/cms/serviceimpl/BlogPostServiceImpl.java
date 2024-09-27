package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.UnAuthorizedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepo;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.ContributionalPanelRepo;
import com.example.cms.repository.UserRepo;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogPostServiceImpl implements BlogPostService{

	private BlogPostRepo blogPostRepo;
	private ResponseStructure<BlogPostResponse> structure;
	private BlogRepo blogRepo;
	private UserRepo userRepo;
	private ContributionalPanelRepo panelRepo;

	public BlogPostServiceImpl(BlogPostRepo blogPostRepo, ResponseStructure<BlogPostResponse> structure,
			BlogRepo blogRepo, UserRepo userRepo, ContributionalPanelRepo panelRepo) {
		super();
		this.blogPostRepo = blogPostRepo;
		this.structure = structure;
		this.blogRepo = blogRepo;
		this.userRepo = userRepo;
		this.panelRepo = panelRepo;
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId, BlogPostRequest blogPostRequest) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(username).map(user -> {
			return blogRepo.findById(blogId).map(blog -> {
				if(!blog.getUser().getEmail().equals(username)&&!panelRepo.existsByPanelIdAndContributors(blog.getPanel().getPanelId(),user))
					throw new BlogNotFoundByIdException("Could Not create blog post");

				BlogPost blogPost=mapToBlogPost(blogPostRequest, new BlogPost());
				blogPost.setPostType(PostType.DRAFT);
				blogPost.setBlog(blog);

				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost created successfully")
						.setBody(mapToBlogPostResponse(blogPost)));


			}).orElseThrow(() -> new BlogNotFoundByIdException("Could not create Blog Post"));
		}).orElseThrow(() -> new UserNotFoundByIdException("Could not create Blog Post"));

	}

	private BlogPost mapToBlogPost(BlogPostRequest blogPostRequest, BlogPost blogPost) {
		blogPost.setTitle(blogPostRequest.getTitle());	
		blogPost.setSubTitle(blogPostRequest.getSubTitle());
		blogPost.setSummary(blogPostRequest.getSummary());
		blogPost.setPostType(blogPostRequest.getPostType());

		return  blogPost; 
	}

	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.postId(blogPost.getPostId())
				.title(blogPost.getTitle())
				.subTitle(blogPost.getSubTitle())
				.summary(blogPost.getSummary())
				.postType(blogPost.getPostType())
				.createdAt(blogPost.getCreatedAt())
				.createdBy(blogPost.getCreatedBy())
				.lastModifiedAt(blogPost.getLastModifiedAt())
				.lastModifiedBy(blogPost.getLastModifiedBy())
				.blog(blogPost.getBlog())
				.build();		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,
			BlogPostRequest blogPostRequest) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return  blogPostRepo.findById(postId).map(exPost->{

			return userRepo.findByEmail(username).map(user -> {

				if(!exPost.getBlog().getUser().getEmail().equals(username) && !panelRepo.existsByPanelIdAndContributors(exPost.getBlog().getPanel().getPanelId(), user))
					throw new BlogPostNotFoundByIdException("Cannot Update Blog Post");

				BlogPost blogPost = mapToBlogPost(blogPostRequest, new BlogPost());
				blogPost.setPostType(PostType.DRAFT);
				blogPost.setPostId(exPost.getPostId());

				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("Post updated successfully")
						.setBody(mapToBlogPostResponse(blogPostRepo.save(blogPost))));

			}).orElseThrow(() -> new UsernameNotFoundException("This is not the owner or contributor"));
		}).orElseThrow(() -> new BlogPostNotFoundByIdException("The blog post is not found by given Id: " + postId));

	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepo.findById(postId).map(post->{
			return userRepo.findByEmail(username).map(user->{

				if(!post.getBlog().getUser().getEmail().equals(username) && !panelRepo.existsByPanelIdAndContributors(post.getBlog().getPanel().getPanelId(), user))
					throw new UnAuthorizedException("un authorized accesss");

				blogPostRepo.delete(post);

				Blog blog=blogRepo.findById(post.getBlog().getBlogId()).get();

				blog.getPosts().remove(post);
				blogRepo.save(blog);

				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("blog post deleted successfully")
						.setBody(mapToBlogPostResponse(post)));

			}).orElseThrow(()-> new UnAuthorizedException("unauthorized access"));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("The blog post you are searching for is not available"));
	}

}
