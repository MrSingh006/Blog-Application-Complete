package com.blogAppLicationComplete.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	
	@NotEmpty
	@Size(min=2,max=100,message = "Min size is 2 and max is 100")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=2,max=200,message = "Min size is 2 and max is 200")
	private String categoryDescription;
}
