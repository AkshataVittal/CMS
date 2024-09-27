package com.example.cms.dto;

import java.util.ArrayList;
import java.util.List;
import com.example.cms.model.User;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContributionPanelResponse {
	
	private int panelId;
	private List<User> contributors=new ArrayList<User>();

}
