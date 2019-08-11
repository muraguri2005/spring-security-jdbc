package org.freelesson.springsecurityjdbc.service;

import java.util.Optional;

import org.freelesson.springsecurityjdbc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	User createUser(User user) throws Exception;
	Page<User> listUsers(Pageable pageable);
	Optional<User> findById(Long userId);
}
