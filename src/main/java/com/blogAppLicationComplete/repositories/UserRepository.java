package com.blogAppLicationComplete.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogAppLicationComplete.entities.User;

/*	It is an interface which  extends JpaRepository interface, which has its implementation
 *  class SimpleJpaRepository which is already annotated with @Repository 
 *  and @Transactional annotation so we do not require to annotate our interface with
    these annotations. Furthermore it provides inbuilt methods for save,update,delete,fetch etc. */

public interface UserRepository extends JpaRepository<User, Integer> {
	
	/* we are making this method so that we can use email as user-name */
	public Optional<User> findByEmail(String email);
	
}
