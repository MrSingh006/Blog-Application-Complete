package com.blogAppLicationComplete.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogAppLicationComplete.entities.Category;
import com.blogAppLicationComplete.exceptions.ResourceNotFoundException;
import com.blogAppLicationComplete.payloads.CategoryDto;
import com.blogAppLicationComplete.repositories.CategoryRepository;
import com.blogAppLicationComplete.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = categoryDtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);
		return categoryToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(int categoryDtoId, CategoryDto categoryDto) {
		Category category = this.categoryRepo.findById(categoryDtoId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryDtoId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		return categoryToCategoryDto(updatedCategory);
	}

	@Override
	public CategoryDto getSingleCategory(int categoryDtoId) {
		Category category = this.categoryRepo.findById(categoryDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryDtoId));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtoList = categoryList.stream()
				.map(category -> categoryToCategoryDto(category)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public void deleteCategory(int categoryDtoId) {
		Category category = this.categoryRepo.findById(categoryDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryDtoId));
		this.categoryRepo.delete(category);
	}
	
	private Category categoryDtoToCategory(CategoryDto categoryDto)
	{
		Category category = this.modelMapper.map(categoryDto, Category.class); 
		return category;
	}
	
	private CategoryDto categoryToCategoryDto(Category category)
	{
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
