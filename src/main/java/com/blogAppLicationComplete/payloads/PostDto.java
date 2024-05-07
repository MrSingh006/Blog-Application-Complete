package com.blogAppLicationComplete.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private int postId;
	private String postTitle;
	private String content;
	private String imageName;
	private Date addedDate;
	
	/*
	 * Here we are using Dto classes because inside user and category class there
	 * exist a post class, which contains user and category in return, this causes an
	 * infinite loop to occur, but inside userDto and postDto there is no post object 
	 * so it works fine
	 */ 
	
	private CategoryDto category;
	private UserDto user;
	
	/*
	 * We are writing comments here because as we will select post we will
	 * automatically get its all comments
	 */
	private Set<CommentDto> comment = new HashSet<>();

}
