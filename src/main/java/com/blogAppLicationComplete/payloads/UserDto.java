package com.blogAppLicationComplete.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blogAppLicationComplete.entities.Post;
import com.blogAppLicationComplete.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	/*
	 * We make Dto class of an entity because we do not expose our Entity class as
	 * entity class will only be used to perform database operations, instead we make
	 * Dto class that can be exposed to client to get information from client or to
	 * show information to client.
	 */ 
	
	/* We validate the class in which data is coming in our case it is dto class */

	private int id;
	
	@NotEmpty
	@Size(min=2,message = "UserName must be min of 2 characters")
	private String name;
	
	@Email(message = "Your email must be valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 15,message = "Password must be min 3 and max 15 character long")
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
