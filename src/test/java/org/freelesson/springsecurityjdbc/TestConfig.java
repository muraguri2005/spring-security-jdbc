package org.freelesson.springsecurityjdbc;

import java.util.Collections;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class TestConfig {
	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		User basicUser=new User("user@example.com", "userPass!2#", Collections.emptyList());
		return new InMemoryUserDetailsManager(basicUser);
	}
}
