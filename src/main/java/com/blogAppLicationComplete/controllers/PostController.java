package com.blogAppLicationComplete.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogAppLicationComplete.config.AppConstants;
import com.blogAppLicationComplete.payloads.ApiResponse;
import com.blogAppLicationComplete.payloads.PostDto;
import com.blogAppLicationComplete.payloads.PostResponse;
import com.blogAppLicationComplete.services.ImageUploadService;
import com.blogAppLicationComplete.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageUploadService imageUpload;
	
	/* this "project.image" is from application.properties file */
	@Value("${project.image}")
	private String path;

	/* Create Post */
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId, @PathVariable Integer categoryId)
	{
		PostDto createdPost = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	/* Update Post */
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
			@PathVariable Integer postId)
	{
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	/* Get Single Post */
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId)
	{
		PostDto singlePost = this.postService.getSinglePost(postId);
		return new ResponseEntity<PostDto>(singlePost,HttpStatus.OK);
	}
	
	/* Get All Posts */
	/* Note: pageNumber starts with 0 whereas pageSize starts with 1 */
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(
		@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
		@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
		@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
		@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
	{
		PostResponse postResponse = this.postService
				.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	/* Get post by user */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> postDtoList = this.postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
	}
	
	/* Get post by Category */
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postDtoList = this.postService.getAllPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
	}
	
	@DeleteMapping("/{postDtoId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postDtoId)
	{
		this.postService.deletePost(postDtoId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true)
				,HttpStatus.OK);
	}
	
	/* Search Post by Keyword */
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword)
	{
		List<PostDto> postDtoList = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
	}
	
	/* Search Post by custom Query */
	@GetMapping("/custom/{keyword}")
	public ResponseEntity<List<PostDto>> customQuery(@PathVariable String keyword)
	{
		List<PostDto> postDtoList = this.postService.customQuery(keyword);
		return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
	}
	
	/* Post image Upload */
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@PathVariable Integer postId,
			@RequestParam("image") MultipartFile image) throws IOException
	{
		// get post in which we want to save the image name
		PostDto postDto = this.postService.getSinglePost(postId);
		
		// to get name of the file(image)
		String fileName = this.imageUpload.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	// method to get images
	/* NOTE: check this URl in chrome not in postman with appropriate image name from database*/
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resource = this.imageUpload.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
