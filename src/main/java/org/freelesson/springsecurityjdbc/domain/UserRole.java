package org.freelesson.springsecurityjdbc.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@IdClass(AssignedRoleId.class)
public class UserRole {
	/**
	 * 
	 */

	@Id
	@Column(nullable = false,name = "user_id")
	public Long userId;
	@Id
	@Column(nullable = false,name = "role_id")
	public Long roleId;

}
