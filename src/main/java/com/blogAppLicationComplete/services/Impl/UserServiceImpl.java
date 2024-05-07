package com.blogAppLicationComplete.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogAppLicationComplete.config.AppConstants;
import com.blogAppLicationComplete.entities.Role;
import com.blogAppLicationComplete.entities.User;
import com.blogAppLicationComplete.exceptions.ResourceNotFoundException;
import com.blogAppLicationComplete.payloads.UserDto;
import com.blogAppLicationComplete.repositories.RoleRepository;
import com.blogAppLicationComplete.repositories.UserRepository;
import com.blogAppLicationComplete.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
//	Very Important Question ?
	/*
	 * Here we are auto-wiring an Interface which is not possible as we can only
	 * auto-wire classes not interfaces so how we are doing it??.
	 * ANS : See in src/test/java
	 */  
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		/*
		 * Taking UserDto object and converting it to User object in order to use it inside
		 * save method of userRepository(JpaRepository) method
		 */
		User user = dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userDtoid) {
		User user = this.userRepo.findById(userDtoid)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userDtoid));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDto updatedUserDto = userToDto(updatedUser);
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(int userDtoid) {
		/*
		 * We can directly use userDtoid without converting it to user object as it is
		 * an integer value we are not passing any object
		 */
		User user = this.userRepo.findById(userDtoid)
		.orElseThrow(()-> new ResourceNotFoundException("User", "Id",userDtoid));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		List<UserDto> UserDtoList = userList
				.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return UserDtoList;
	}

	@Override
	public void deleteUser(int userDtoid) {
		User user = this.userRepo.findById(userDtoid)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userDtoid));
		this.userRepo.delete(user);
	}
	
	
	/* NOTE: ModelMapper map method takes 2 arguments 1 class which we want to convert and
	 * 2 to which we want to convert*/
	
	/* Creating new method to convert Dto to user */
	private User dtoToUser(UserDto userDto)
	{
		
		User user = this.modelMapper.map(userDto,User.class);
		
//		User user = new User();
//		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}
	
	/* Creating new method to convert user to Dto */
	private UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto = new UserDto();
//		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}

	/* NOTE: Difference between registerUser & createUser is that registerUser also manages 
	 * password and role of user during creation. Its controller is in AuthController*/
	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		// encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// role
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User createdUser = this.userRepo.save(user);
		
		return this.modelMapper.map(createdUser, UserDto.class);
	}

}
