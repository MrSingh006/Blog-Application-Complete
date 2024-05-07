package com.blogAppLicationComplete.config;

import org.springdoc.webmvc.api.OpenApiActuatorResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


/* we can get our complete documentation of our application by going to the following link :
 * "http://localhost:8080/swagger-ui/index.html" in our web browser*/

/*NOTE: refer link:- "https://springdoc.org/migrating-from-springfox.html"*/

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi()
	{
		return new OpenAPI()
				.info(new Info()
						.title("Blog Application Complete")
						.description("This is blog application backend Api")
						.version("1.0")
						.contact(new Contact().name("Paurav").email("noice@gmail")))
				.externalDocs(new ExternalDocumentation().url("Refer above note"));
	}
	

//	@Bean
//	public GroupedOpenApi publicApi() 
//	{
//		return GroupedOpenApi
//				.builder()
//				.group(getInfo())
//				.pathsToMatch("/public/**")
//				.build();
//	}
	
//	private String getInfo()
//	{
//			
//	}
	
//	@Bean
//	  public OpenAPI openApi() {
//	      return new OpenAPI()
//	  }

//	@Bean
//	public GroupedOpenApi adminApi() {
//		return GroupedOpenApi
//				.builder()
//				.group("springshop-admin")
//				.pathsToMatch("/admin/**")
//				.addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class)).build();
//	}
	
	
}
