package org.freelesson.springsecurityjdbc.service;

import org.freelesson.springsecurityjdbc.domain.UserRole;

public interface UserRoleService {
	UserRole createUserRole(UserRole userRole) throws Exception;
}
