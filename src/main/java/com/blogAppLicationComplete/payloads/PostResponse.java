package com.blogAppLicationComplete.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*  We are making this class as we need appropriate response when fetching data of all posts
 *  using pagination. As of now we are getting all posts as list but we need information like
 *  page number if its last page or not and many such details to show.*/

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isLastPage;
}
