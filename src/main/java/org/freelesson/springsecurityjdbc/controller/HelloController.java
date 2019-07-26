package org.freelesson.springsecurityjdbc.controller;

import java.security.Principal;

import org.freelesson.springsecurityjdbc.domain.Welcome;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public @ResponseBody Welcome greetings(@AuthenticationPrincipal Principal principal, @RequestParam String name ) {
		return new Welcome( "Welcome "+name);
	}

}
