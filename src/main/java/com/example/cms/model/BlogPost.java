package com.example.cms.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.cms.enums.PostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="blogpost")
@Getter
@Setter
public class BlogPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@NotNull(message = "Enter should not be null")
	private String title;
	private String subTitle;
	@Min(value = 500,message = "Enter atleast 500 charracters")
	@Max(value = 1000,message = "enter maximum of 400 characters only")
	private String summary;
	@Enumerated(EnumType.STRING)
	private PostType postType;
	
	@CreatedBy
	@Column
    private LocalDateTime createdAt;
	@CreatedBy
    private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	@LastModifiedBy
	private String lastModifiedBy;
	
	@ManyToOne
	private Blog blog;

}
