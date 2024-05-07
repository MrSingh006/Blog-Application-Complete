package com.blogAppLicationComplete.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter	{
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		We are requesting token from header and our key word will be "Authorization"
		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		
		//Bearer 2352345235sdfrsfgsdfsdf : this is our token
		
		String userName = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			/* We will now fetch actual token from "Bearer 2352345235sdfrsfgsdfsdf". Actual
			 * token being "2352345235sdfrsfgsdfsdf". So we have to remove Bearer and space from 
			 * our token. Here 7 because Actual token starts from 7th position in out token string.*/
			token = requestToken.substring(7);
			try 
			{
				userName = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException ex){
				System.out.println("Unable to get Jwt Token "+ex);
			}
			catch(ExpiredJwtException ex){
				System.out.println("Jwt token expired "+ex);
			}
			catch(MalformedJwtException ex){
				System.out.println("Jwt token not formed correctly "+ex);
			}
		}
		else
		{
			System.out.println("Jwt Token does not starts with Bearer !!");
		}
		
		
		/* Once we get the token now we will validate it. To validate we will check if  userName 
		 * is not null and token is not Authenticated previously so we can authenticate it ourselves.*/
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
//				Now we Authenticate our token
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null , 
								userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext()
				.setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("Invalid Jwt Token !!");
			}
		}
		else
		{
			System.out.println("UserName is null or context is not null!!");
		}
		
		filterChain.doFilter(request, response);
	}

}
