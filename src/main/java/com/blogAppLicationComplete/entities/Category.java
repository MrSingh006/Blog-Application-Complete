package com.blogAppLicationComplete.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Catergories")
@Getter
@Setter
@NoArgsConstructor

/*
 * NOTE: we can use @Data annotation also instead
 * of @Getter, @Setter, @NoArgsConstructor, but we are not using it as it will
 * also give us toString method that we do not require as of now
 */ 
public class Category { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	
	@Column(name="Title",length=100,nullable = false)
	private String categoryTitle;
	
	@Column(name="Description")
	private String categoryDescription;
	
	/*
	 * One category can have many posts so we are using a list here and annotating
	 * it with @OneToMany annotation. We are using mappedBy to write the name of column
	 * with which it is mapped in Post class and mentioning cascade will change child
	 * if parent is changed
	 */
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
}
