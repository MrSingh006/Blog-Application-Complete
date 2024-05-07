package com.blogAppLicationComplete.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogAppLicationComplete.exceptions.ApiException;
import com.blogAppLicationComplete.payloads.JwtAuthRequest;
import com.blogAppLicationComplete.payloads.JwtAuthResponse;
import com.blogAppLicationComplete.payloads.UserDto;
import com.blogAppLicationComplete.security.JwtTokenHelper;
import com.blogAppLicationComplete.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtAuthResponse jwtAuthResponse;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
	{
		this.authenticationManager(request.getUserName(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		this.jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	}

	private void authenticationManager(String userName, String password) throws Exception 
	{
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(userName, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} 
		catch (BadCredentialsException ex) {
			System.out.println("Username or Password incorrect !!"+ex);
			throw new ApiException("Invalid Credentials");
		}
	}
	
	//Register new User
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto registeredUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	

}
