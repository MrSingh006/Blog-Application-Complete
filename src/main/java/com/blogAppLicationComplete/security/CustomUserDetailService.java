package com.blogAppLicationComplete.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogAppLicationComplete.entities.User;
import com.blogAppLicationComplete.exceptions.ResourceNotFoundException;
import com.blogAppLicationComplete.repositories.UserRepository;

/* We are making this class so that we can use our custom user-name and password 
 * from database. This class also implements UserDetailsService interface, 
 * this interface loads user specific data*/

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*loading user from database by user-name, PS: we are using email as user-name*/
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Email : "+username, 0));
		
		/* We have to return UserDetails but we are getting user to correct it we will
		 * make changes in User entity class after that we can return "user" instead of
		 * "UserDetails". */
		
		return user;
	}

}
