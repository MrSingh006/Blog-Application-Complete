package com.blogAppLicationComplete.services;

import com.blogAppLicationComplete.payloads.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	public void deleteComment(Integer commentId);
}
