package com.blogAppLicationComplete.services;

import java.util.List;

import com.blogAppLicationComplete.payloads.PostDto;
import com.blogAppLicationComplete.payloads.PostResponse;

public interface PostService {
	
	/* During creation of post we are taking userId as well as categoryId as well */
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);
	public PostDto updatePost(PostDto postDto, Integer postDtoId);
	public PostDto getSinglePost(Integer postDtoId);
	
//	public List<PostDto> getAllPosts(int pageNumber,int pageSize);
	
	/* We are changing return type to PostResponse instead of List<PostDto> for
	 * proper response that includes page number etc.*/
	public PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	public void deletePost(Integer postDtoId);
	
	// get all posts by Category
	public List<PostDto> getAllPostsByCategory(Integer categoryDtoId);
	
	//get all posts by User
	public List<PostDto> getAllPostsByUser(Integer userDtoId);
	
	//search posts using keyword
	public List<PostDto> searchPost(String keyword);
	
	//search by custom query
	public List<PostDto> customQuery(String keyword);
}
