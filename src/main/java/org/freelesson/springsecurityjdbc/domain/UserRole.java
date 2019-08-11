package org.freelesson.springsecurityjdbc.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_role")
@IdClass(AssignedRoleId.class)
public class UserRole {
	/**
	 * 
	 */

	@Id
	@Column(nullable = false,name = "user_id")
	@NotNull(message="userId is required")
	public Long userId;
	@Id
	@Column(nullable = false,name = "role_id")
	@NotNull(message="roleId is required")
	public Long roleId;

}
