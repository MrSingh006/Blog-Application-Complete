package com.blogAppLicationComplete.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter

/* We are implementing "UserDetails" class of spring framework so that we can return
 * UserDetails in "CustomUserDetailService" and add and modify its unimplemented methods*/
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="user_name",nullable = false,length = 100)
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="about")
	private String about;
	
	/*
	 * One category can have many posts so we are using a list here and annotating
	 * it with @OneToMany annotation. We are using mappedBy to write the name of column
	 * with which it is mapped in Post class and mentioning cascade will change child
	 * if parent is changed
	 */
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns = @JoinColumn(name="user",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		/* We will take each and every role and change it in granted authority */
		
		List<SimpleGrantedAuthority> authorities = this.roles.stream()
				.map(role->new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		/// changing all to True
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// changing all to True
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// changing all to True
		return true;
	}

	@Override
	public boolean isEnabled() {
		// changing all to True
		return true;
	} 
}
