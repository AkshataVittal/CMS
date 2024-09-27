package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

	boolean existsByTittle(String tittle);

	boolean existsByUserAndPanel(User owner, ContributionPanel panel);


}
