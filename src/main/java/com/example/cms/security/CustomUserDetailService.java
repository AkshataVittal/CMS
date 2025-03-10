package com.example.cms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.cms.repository.UserRepo;
import lombok.AllArgsConstructor;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	private UserRepo userRepo;
	
	

	public CustomUserDetailService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByEmail(username)
				.map(user->new CustomUserDetail(user))
				.orElseThrow(()->new UsernameNotFoundException("username not found"));
	}
}
