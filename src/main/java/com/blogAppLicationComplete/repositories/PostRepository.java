package com.blogAppLicationComplete.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogAppLicationComplete.entities.Category;
import com.blogAppLicationComplete.entities.Post;
import com.blogAppLicationComplete.entities.User;

/* To apply paging and sorting we don't have to do much as JpaRepository already 
 * extends ListPagingAndSortingRepository so we have already all methods with us,
 * we just need to use them */
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	/* In this we will create custom finder methods according to our mapping */
	
	/* This method will give all posts associated with given user */
	public List<Post> findByUser(User user);
	
	/* This method will give all posts associated with given category */
	public List<Post> findByCategory(Category category);
	
//	This method will allow us to search posts according to our title
	/*
	 * Note : Write the name very carefully "findByPostTitleContaining" there should
	 * be no spelling mistake in this word sequence should be same and since we are
	 * searching by postTitle we have to write "PostTitle" in name as it is
	 * otherwise code will not compile and it will throw error. If we want to search 
	 * by some other means then we need to write that name in method name.
	 */
	/* Original working fine */
	public List<Post> findByPostTitleContaining(String postTitle);
//	public List<Post> findByContentContaining(String Content);
	
//	if we want to write our specific query we do it like below
	@Query(value = "select * from Post where post_title like :key",nativeQuery = true)
	public List<Post> searchByCustomQuery(@Param("key") String postTitle);

}
