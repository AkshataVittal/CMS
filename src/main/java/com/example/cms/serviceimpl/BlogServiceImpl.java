package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.cms.dto.BlogRequest;
import com.example.cms.dto.BlogResponse;
import com.example.cms.exception.BlogAlreadyExistsByTittleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.ContributionalPanelRepo;
import com.example.cms.repository.UserRepo;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogServiceImpl implements BlogService{

	private BlogRepo blogRepo;
	private ResponseStructure<BlogResponse> structure;
	private UserRepo userRepo;
	private ContributionalPanelRepo panelRepo;


	public BlogServiceImpl(BlogRepo blogRepo, ResponseStructure<BlogResponse> structure, UserRepo userRepo,
			ContributionalPanelRepo panelRepo) {
		super();
		this.blogRepo = blogRepo;
		this.structure = structure;
		this.userRepo = userRepo;
		this.panelRepo = panelRepo;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest) {
		return userRepo.findById(userId).map(user->{

			if(blogRepo.existsByTittle(blogRequest.getTittle()))
				throw new BlogAlreadyExistsByTittleException("failed to create blog");

			if(blogRequest.getTopics().length<1)
				throw new TopicNotSpecifiedException("failed to create blog");

			Blog blog=mapToBlogRequest(blogRequest,new Blog());
			ContributionPanel panel=new ContributionPanel();
			
			panel.getContributors().add(user);
			user.getBlogList().add(blog);
			blog.setUser(user);
			blog.setPanel(panel);
			
			panelRepo.save(panel);
			userRepo.save(user);
			blogRepo.save(blog);
			
			return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
					.setMessage("blog created successfully")
					.setBody(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new UserNotFoundByIdException("User not exist with given Id"));

	}
		
	private Blog mapToBlogRequest(BlogRequest blogRequest, Blog blog) {
		blog.setTittle(blogRequest.getTittle());	
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		blog.setUser((User)blogRequest.getUserList());
		
		return  blog; 
	}

	private BlogResponse mapToBlogResponse(Blog blog) {
		return BlogResponse.builder()
				.blogId(blog.getBlogId())
				.tittle(blog.getTittle())
				.topics(blog.getTopics())
				.about(blog.getAbout())
				.build();		
	}

	@Override
	public ResponseEntity<Boolean> checkTitleAvailability(String tittle) {
		return new ResponseEntity<Boolean>(blogRepo.existsByTittle(tittle),HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return blogRepo.findById(blogId).map(blog->{
			return ResponseEntity.ok(structure.setMessage("Blog found success")
					.setStatus(HttpStatus.OK.value())
					.setBody(mapToBlogResponse(blog)));
		})
				.orElseThrow(()-> new BlogNotFoundByIdException("invalid blog id"));

	}


	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(int blogId, BlogRequest blogRequest) {
		return blogRepo.findById(blogId).map(exBlog->{

			if(blogRepo.existsByTittle(blogRequest.getTittle())) 
				throw new TitleAlreadyExistsException("Failed to update the blog");
			if(blogRequest.getTopics().length<1) 
				throw new TopicNotSpecifiedException("Failed to update the blog");
			Blog blog=mapToBlogRequest(blogRequest, exBlog);
			
			blog.setBlogId(exBlog.getBlogId());

			return ResponseEntity.ok(structure.setMessage("update successfully")
					.setStatus(HttpStatus.OK.value())
					.setBody(mapToBlogResponse(blog)));

		}).orElseThrow(()-> new BlogNotFoundByIdException("failed to update the blog"));
	}
}
		