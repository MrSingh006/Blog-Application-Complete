package com.blogAppLicationComplete.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	@Column(name="post_title",length =100,nullable = false)
	private String postTitle;
	
	@Column(length =10000)
	private String content;
	private String imageName;
	private Date addedDate;
	
	/*
	 * See diagram we need to map post to category and user, such that one post can
	 * have one category but one category can have multiple posts, same with user so 
	 * to do that we are creating a field of category and user here. @ManyToOne b/c
	 * many posts can have one category and user. @JoinColumn is to rename column 
	 * in post table of database.
	 */
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private Set<Comment> comment = new HashSet<>();
}
