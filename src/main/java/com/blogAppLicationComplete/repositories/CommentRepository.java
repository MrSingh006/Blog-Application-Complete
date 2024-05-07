package com.blogAppLicationComplete.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogAppLicationComplete.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
