package com.blogAppLicationComplete.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
	
	public String uploadImage(String path,MultipartFile file) throws IOException;
	
	public InputStream getResource(String path,String fileName) throws FileNotFoundException;

}
