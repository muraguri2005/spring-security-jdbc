package org.freelesson.springsecurityjdbc.service.impl;

import org.freelesson.springsecurityjdbc.domain.User;
import org.freelesson.springsecurityjdbc.exception.BadRequestException;
import org.freelesson.springsecurityjdbc.repository.UserRepository;
import org.freelesson.springsecurityjdbc.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Wrong username or password"));
        return new org.springframework.security.core.userdetails.User(user.username,user.password, getAuthorities(user));
    }
    List<SimpleGrantedAuthority> getAuthorities(User user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.roles.forEach((role) -> authorities.add(new SimpleGrantedAuthority(role.name)));
		return authorities;
	}
    
    @Override
    public User createUser(User user) throws Exception {
    	if (userRepository.findByUsername(user.username).isPresent())
    		throw new BadRequestException("Username is already used");
    	if (user.password!=null)
    		user.password=passwordEncoder.encode(user.password);
    	return userRepository.save(user);
    }
    
    @Override
    public Page<User> listUsers(Pageable pageable) {
    	return userRepository.findAll(pageable);
    }
    
    @Override
    public Optional<User> findById(Long userId) {
    	return userRepository.findById(userId);
    }
}
