package com.blogAppLicationComplete.exceptions;

import lombok.Getter;
import lombok.Setter;

/*We are extending this class with RuntimeException because we have to handle unchecked exceptions*/

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fieldName;
	long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		/*
		 * We can pass the message that we want to display in super method using
		 * String.format method
		 */
		super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
