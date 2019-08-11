package org.freelesson.springsecurityjdbc.controller;

import org.freelesson.springsecurityjdbc.domain.User;
import org.freelesson.springsecurityjdbc.exception.ObjectNotFoundException;
import org.freelesson.springsecurityjdbc.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody User createUser(@RequestBody User user) throws Exception {
		return userService.createUser(user);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody Page<User> listUsers(Pageable pageable) throws Exception {
		return userService.listUsers(pageable);
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody User getUserById(@PathVariable Long userId) throws Exception {
		return userService.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User not found"));
	}
}
