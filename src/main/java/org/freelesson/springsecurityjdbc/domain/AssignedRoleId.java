package org.freelesson.springsecurityjdbc.domain;

import java.io.Serializable;


public class AssignedRoleId implements Serializable {
	private static final long serialVersionUID = 3075036910012257493L;
	public Long userId;
	public Long roleId;
	public AssignedRoleId() {
	}
	
	public AssignedRoleId(Long userId,Long roleId) {
		this.roleId = roleId;
		this.userId = userId;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AssignedRoleId))
			return false;
		AssignedRoleId assignedRoleId=(AssignedRoleId)obj;
		return this.roleId == assignedRoleId.roleId && this.userId == assignedRoleId.userId;
	}
	
	@Override
	public int hashCode() {
		return Integer.parseInt(String.valueOf(roleId).concat(String.valueOf(userId)));
	}
}