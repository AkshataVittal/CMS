package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.model.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface ContributionalPanelService {

	ResponseEntity<ResponseStructure<ContributionPanel>> addContributors(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId);

}
