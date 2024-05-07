package com.blogAppLicationComplete.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*We are making this class @Component so that we can autoWire it*/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	/* This method will be called when an unauthorized user will try to access our
	 * authenticated Api. It will send error back to that user.*/

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException 
	{
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access denied !!");
	}

}
