package com.blogAppLicationComplete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogAppLicationComplete.security.CustomUserDetailService;
import com.blogAppLicationComplete.security.JwtAuthenticationEntryPoint;
import com.blogAppLicationComplete.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
/* We are using @EnableGlobalMethodSecurity for role based authentication. i.e. only those users
 * that have some specific role will be able to use some API's. Alternative of 
 * @EnableGlobalMethodSecurity(prePostEnabled = true) is @EnableMethodSecurity*/
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",		// for login-api
			"/v3/api-docs",         // for swagger
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};

//	protected void configure(HttpSecurity http) throws Exception 
//	{
//		http
//		.csrf()
//		.disable()
//		.authorizeHttpRequests()
//		.antMatchers("/api/v1/auth/login").permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	}
	
	/* Below method is working completely fine but I'am commenting it because 
	 * authorizeRequests() is depreciated */	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable())
//                .authorizeRequests()
////                .requestMatchers("/test").authenticated()
//                .requestMatchers("/api/v1/auth/**").permitAll()
//                .requestMatchers("/v2/api-docs").permitAll() // for swagger
//                .requestMatchers(HttpMethod.GET).permitAll() // Now everyone will be able to access all GET methods without authorization
//                .anyRequest()
//                .authenticated()
//                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                		.requestMatchers(PUBLIC_URLS).permitAll()
                		.requestMatchers(HttpMethod.GET).permitAll() // Now everyone will be able to access all GET methods without authorization
                		.anyRequest()
                		.authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

	/*
	 * We are overriding this method because we want to configure our application
	 * with the help of database
	 */

	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	/*
	 * We will use password encoder also so all passwords will be stored in BCrypt
	 * format in our database
	 */
	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
	

}
