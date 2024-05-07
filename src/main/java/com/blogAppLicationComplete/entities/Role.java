package com.blogAppLicationComplete.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {
	
	/* Note: We are commenting auto generation of ID in our roles as our roles will be created
	 * at the time of starting application through BlogApplicationCompleteApplication file*/
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private int roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
}
