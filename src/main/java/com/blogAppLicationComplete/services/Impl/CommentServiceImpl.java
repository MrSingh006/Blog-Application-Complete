package com.blogAppLicationComplete.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogAppLicationComplete.entities.Comment;
import com.blogAppLicationComplete.entities.Post;
import com.blogAppLicationComplete.exceptions.ResourceNotFoundException;
import com.blogAppLicationComplete.payloads.CommentDto;
import com.blogAppLicationComplete.repositories.CommentRepository;
import com.blogAppLicationComplete.repositories.PostRepository;
import com.blogAppLicationComplete.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto,Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment createdComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(createdComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
	}

}
