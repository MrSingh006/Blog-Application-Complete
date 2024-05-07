package com.blogAppLicationComplete.config;

/* We are making this class so that we do not have to hard code any value in our 
 * spring application, as it is a good practice to use all constants in a file and
 * not hard code it in our application*/

public class AppConstants {
	/* Now to make constants */
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "5";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "asc";
	public static final Integer ADMIN_USER = 501;  // ROLE_ADMIN
	public static final Integer NORMAL_USER = 502;
}
