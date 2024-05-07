package com.blogAppLicationComplete.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogAppLicationComplete.payloads.ApiResponse;
import com.blogAppLicationComplete.payloads.CategoryDto;
import com.blogAppLicationComplete.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto savedCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(savedCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryDtoId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, 
			@PathVariable("categoryDtoId") Integer cId)
	{
		CategoryDto updatedCategory = this.categoryService.updateCategory(cId, categoryDto);
		return ResponseEntity.ok(updatedCategory);
	}
	
	@GetMapping("/{categoryDtoId}")
	public ResponseEntity<CategoryDto> getSingleUser(@PathVariable("categoryDtoId") Integer cId)
	{
		CategoryDto getCateroDto = this.categoryService.getSingleCategory(cId);
		return new ResponseEntity<CategoryDto>(getCateroDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> categoryList = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(categoryList,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("This category has been Deleted !!",
				true),HttpStatus.OK);
	}
	
}
