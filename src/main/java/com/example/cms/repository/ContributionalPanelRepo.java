package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

@Repository
public interface ContributionalPanelRepo extends JpaRepository<ContributionPanel, Integer> {

	boolean existsByContributors(User contributor);

	boolean existsByPanelIdAndContributors(int panelId, User Contributor);
}
