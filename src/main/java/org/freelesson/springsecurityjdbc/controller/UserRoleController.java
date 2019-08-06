package org.freelesson.springsecurityjdbc.controller;

import org.freelesson.springsecurityjdbc.domain.UserRole;
import org.freelesson.springsecurityjdbc.service.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userrole")
public class UserRoleController {
	final UserRoleService userRoleService;
	public UserRoleController(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody UserRole createUserRole(@RequestBody UserRole userRole) throws Exception {
		return userRoleService.createUserRole(userRole);
	}
}
