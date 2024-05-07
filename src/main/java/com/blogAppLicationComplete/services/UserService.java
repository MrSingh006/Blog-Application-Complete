package com.blogAppLicationComplete.services;

import java.util.List;

import com.blogAppLicationComplete.payloads.UserDto;

public interface UserService {
	
	/*
	 * We can write like this also no problem but we will not write like this. We
	 * will create new UserDto(Data Transfer Object) class inside payLoad package
	 * and we will expose that class. Entity class is only to put data into database.
	 */
	
//	public User createUser(User user);
//	public User updateUser(User user,int id);
//	public User getUserById(int id);
//	public List<User> getAllUsers();
//	public void deleteUser(int id);
	
	/* NOTE: Difference between registerUser & createUser is that registerUser also manages 
	 * password and role of user during creation*/
	
	public UserDto registerUser(UserDto userDto);
	
	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto,int userDtoid);
	public UserDto getUserById(int userDtoid);
	public List<UserDto> getAllUsers();
	public void deleteUser(int userDtoid);
}
