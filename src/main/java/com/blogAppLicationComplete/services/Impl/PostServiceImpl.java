package com.blogAppLicationComplete.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogAppLicationComplete.entities.Category;
import com.blogAppLicationComplete.entities.Post;
import com.blogAppLicationComplete.entities.User;
import com.blogAppLicationComplete.exceptions.ResourceNotFoundException;
import com.blogAppLicationComplete.payloads.PostDto;
import com.blogAppLicationComplete.payloads.PostResponse;
import com.blogAppLicationComplete.repositories.CategoryRepository;
import com.blogAppLicationComplete.repositories.PostRepository;
import com.blogAppLicationComplete.repositories.UserRepository;
import com.blogAppLicationComplete.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/* We are taking UserRepository and CategoryRepository to fetch user and
	 * category */
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	

	@Override
	public PostDto createPost(PostDto postDto,Integer categoryId, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		/*
		 * We are setting post variables ourselves as in Dto there are not much
		 * variables compare to its Post class
		 */
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("defaut.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postDtoId) {
		Post existingPost =this.postRepo.findById(postDtoId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "id", postDtoId));
		
		existingPost.setPostTitle(postDto.getPostTitle());
		existingPost.setContent(postDto.getContent());
		existingPost.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(existingPost);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getSinglePost(Integer postDtoId) {
		Post post = this.postRepo.findById(postDtoId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "Id", postDtoId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	/* We are changing return type to PostResponse instead of List<PostDto> for
	 * proper response that includes page number etc.*/
	public PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy, String sortDir) {
//		List<Post> postList = this.postRepo.findAll();
		
		/* Apply Sorting */
		Sort sort = sortDir.equalsIgnoreCase("asc")?
				Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		/*Here we will apply pagination*/
		Pageable page = PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(page);
		List<Post> getAllPosts = pagePost.getContent();
		/* Pagination Ends */
		
		List<PostDto> postDtoList = getAllPosts.stream()
				.map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		/* Set values in PostResponse */
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public void deletePost(Integer postDtoId) {
		Post existingPost = this.postRepo.findById(postDtoId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "Id", postDtoId));
		this.postRepo.delete(existingPost);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryDtoId) {
		Category category = this.categoryRepo.findById(categoryDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryDtoId));
		List<Post> postList = this.postRepo.findByCategory(category);
		List<PostDto> postDtoList = postList.stream()
				.map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userDtoId) {
		User user = this.userRepo.findById(userDtoId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userDtoId));
		List<Post> postList = this.postRepo.findByUser(user);
		List<PostDto> postDtolist = postList.stream()
				.map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtolist;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> postList = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDto> postDtoList = postList.stream()
				.map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> customQuery(String keyword) {
		List<Post> postList = this.postRepo.searchByCustomQuery("%"+keyword+"%");
		List<PostDto> postDtoList = postList.stream()
				.map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}
	
}
