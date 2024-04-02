package com.example.cms.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name ="blog",
uniqueConstraints=
@UniqueConstraint(columnNames={"tittle"}))
@Getter
@Setter
@EntityListeners(value = {AuditingEntityListener.class})
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogId;
	private String tittle;
	private String[] topics;
	private String about;
	
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime modifiedAt;
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "blog")
	private List<BlogPost> posts;
	
	@OneToOne
	private ContributionPanel panel;



}
