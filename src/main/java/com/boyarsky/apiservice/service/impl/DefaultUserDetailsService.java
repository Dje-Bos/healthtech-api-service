package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.entity.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        return validateAndReturn(user, username);
    }

    public UserDetails loadUserById(long id) {
        User user = userRepository.getUserById(id);
        return validateAndReturn(user, id);
    }

    private UserDetails validateAndReturn(User user, Object param) {
        if (user == null) {
            throw new UsernameNotFoundException(param.toString());
        }
        else {
            return UserPrincipal.create(user);
        }
    }
}
