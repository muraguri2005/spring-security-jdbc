package org.freelesson.springsecurityjdbc.service;

import org.freelesson.springsecurityjdbc.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRoleService {
	UserRole createUserRole(UserRole userRole) throws Exception;
	Page<UserRole> list(Pageable pageable);
}
