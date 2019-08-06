package org.freelesson.springsecurityjdbc.service.impl;

import org.freelesson.springsecurityjdbc.domain.UserRole;
import org.freelesson.springsecurityjdbc.repository.UserRoleRepository;
import org.freelesson.springsecurityjdbc.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements  UserRoleService {


    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole createUserRole(UserRole userRole) throws Exception {
    	return userRoleRepository.save(userRole);
    }
}
