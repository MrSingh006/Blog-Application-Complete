package com.blogAppLicationComplete.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogAppLicationComplete.payloads.ApiResponse;
import com.blogAppLicationComplete.payloads.UserDto;
import com.blogAppLicationComplete.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	Very Important
	/* Building an API response in the Spring framework can be done using 
	 * ResponseEntity and Response Body annotations. 
	 * Though @ResponseBody will cater to the basic requirements, 
	 * @ResponseEntity annotation provides better control and flexibility in building a response.
	 * When we need to build responses with a lot of customization, then the @ResponseEntity 
	 * would be a better choice. Same goes for writing ResponseEntity<> in method body.*/
	
	/* We are using @valid annotation here to enable validations of our dto class */

	// POST - Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED); 
	}
	
	/*
	 * @PathVariable and @RequestParam do the same thing main difference being how
	 * the url is create for both of them.
	 * 
	 * @PathVariable url = "http://localhost:8080/spring-mvc-basics/foos/ab+c" 
	 * 		O/P = ab+c
	 * @RequestParam url = "http://localhost:8080/spring-mvc-basics/foos?id=ab+c"
	 * 		O/P = ab c
	 */
		
	//PUT - Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer uId)
	{
		UserDto updatedUser = this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE - Delete User
//	@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('ADMIN_USER')")// added for role based authentication
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully !!",true)
				,HttpStatus.OK);
	}
	
	//GET - Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	/*
	 * Note: Here we are not writing @PathVariable("variableName") because name
	 * "userId" and @GetMapping("/{userId}") are same
	 */
	
	//GET - Get Single User
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
	{
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
