package com.blogAppLicationComplete.services;

import java.util.List;

import com.blogAppLicationComplete.payloads.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	public CategoryDto updateCategory(int categoryDtoId, CategoryDto categoryDto);
	public CategoryDto getSingleCategory(int categoryDtoId);
	public List<CategoryDto> getAllCategories();
	public void deleteCategory(int categoryDtoId);
}
