package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.model.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

	boolean existsByTittle(String tittle);

}
