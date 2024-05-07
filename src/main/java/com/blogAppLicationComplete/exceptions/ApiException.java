package com.blogAppLicationComplete.exceptions;

/* This class is handling exceptions that are created at login(AuthController) 
 * on giving bad userName or password*/
public class ApiException extends RuntimeException{

	public ApiException(String message) {
		super(message);
	}

	public ApiException() {
		super();
	}

}
