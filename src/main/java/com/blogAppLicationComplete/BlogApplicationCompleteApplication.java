package com.blogAppLicationComplete;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogAppLicationComplete.config.AppConstants;
import com.blogAppLicationComplete.entities.Role;
import com.blogAppLicationComplete.repositories.RoleRepository;

@SpringBootApplication
public class BlogApplicationCompleteApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationCompleteApplication.class, args);
		System.out.println("Application Started");
	}

	//	We will create bean of modelMapper class here
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		/* We will write the value that we want to encode in "" eg. abc this is written
		 * in database password column of user table */
		System.out.println(this.passwordEncoder.encode("Uzumaki"));
		
		/* During start of application our 2 roles will be created if not already created*/
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setRoleName("ADMIN_USER");
			
			Role role1 = new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setRoleName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->System.out.println(r.getRoleName()));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	Encoded password that we got from console store it in database
	
//	Uzumaki = $2a$10$63Luqk.Vjh9znfkZlJ0VUeZeQRrL/CserLZXQF3CKQ7wPGTBQj/.u
}
