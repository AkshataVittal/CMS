package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.ContributionPanelResponse;
import com.example.cms.exception.ContributionPanelNotFoundByIdException;
import com.example.cms.exception.UnAuthorizedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.ContributionalPanelRepo;
import com.example.cms.repository.UserRepo;
import com.example.cms.service.ContributionalPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContributionalPanelServiceImpl implements ContributionalPanelService {

	private ContributionalPanelRepo panelRepo;
	private UserRepo userRepo;
	private BlogRepo blogRepo;
	private ResponseStructure<ContributionPanel> structure;



	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributors(int userId,int panelId){

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(username).map(owner->{
			return panelRepo.findById(panelId).map(panel->{

				if(!blogRepo.existsByUserAndPanel(owner,panel)) throw new UnAuthorizedException("Illegal accept request");

				return userRepo.findById(userId).map(contributor->{

					if(!panelRepo.existsByContributors(contributor))panel.getContributors().add(contributor);
					panelRepo.save(panel);

					return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
							.setMessage("contributor inserted successfully")
							.setBody(panel));
				}).orElseThrow(()-> new UserNotFoundByIdException("can not insert contributor"));

			}).orElseThrow(()-> new ContributionPanelNotFoundByIdException("can not insert contributor"));
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(username).map(owner->{
			
			return panelRepo.findById(panelId).map(panel->{
				
				if(blogRepo.existsByUserAndPanel(owner, panel)) throw new UnAuthorizedException("Illegal accept request");
				
				return userRepo.findById(userId).map(contributor->{
					if(panel.getContributors().contains(contributor))panel.getContributors().remove(contributor);
					panelRepo.save(panel);
					
					return ResponseEntity.ok(structure.setStatus(HttpStatus.ACCEPTED.OK.value())
							.setMessage("user deleted successfully")
							.setBody(panel));
				}).orElseThrow(()-> new UserNotFoundByIdException("Cannot delete contributo"));
						
			}).orElseThrow(()->new ContributionPanelNotFoundByIdException("Cannot delete contributo"));
		}).get();

}
}
