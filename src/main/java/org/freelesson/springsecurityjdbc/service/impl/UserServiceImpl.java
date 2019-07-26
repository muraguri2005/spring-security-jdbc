package org.freelesson.springsecurityjdbc.service.impl;

import org.freelesson.springsecurityjdbc.domain.User;
import org.freelesson.springsecurityjdbc.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Wrong username or password"));
        return new org.springframework.security.core.userdetails.User(user.username,user.password, getAuthorities(user));
    }
    List<SimpleGrantedAuthority> getAuthorities(User user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.roles.stream().forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.name));
		});
		return authorities;
	}
}
