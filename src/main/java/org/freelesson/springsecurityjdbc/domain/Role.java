package org.freelesson.springsecurityjdbc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	
	public static final String ROLE_USER="ROLE_USER";
	public static final String ROLE_ADMIN="ROLE_ADMIN";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false,unique=true)
	public Long id;
	@Column(nullable=false, unique=true)
	public String name;
}
