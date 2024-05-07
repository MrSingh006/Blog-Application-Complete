package com.blogAppLicationComplete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blogAppLicationComplete.repositories.UserRepository;

@SpringBootTest
class BlogApplicationCompleteApplicationTests {
	

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private UserRepository userRepo;

	@Test
	public void testAutowiringUserReopsitory()
	{
		String className = this.userRepo.getClass().getName();
		String packageName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
		
//		Reason
		/*
		 * Java creates new dynamic class every time we autoWire an interface like
		 * 'jdk.proxy2.$Proxy108' from proxy2 package and uses this to full fill
		 * autowiring in java using interface
		 */
	}
}
