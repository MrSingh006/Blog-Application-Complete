package com.blogAppLicationComplete.payloads;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtAuthRequest {
	
	/* Note: our email is our userName */
	private String userName;
	
	private String password;
}
