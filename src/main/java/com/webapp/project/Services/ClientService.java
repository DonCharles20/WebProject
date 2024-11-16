package com.webapp.project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.webapp.project.models.UsersTable;
import com.webapp.project.models.repositories.UserRepository;

public class ClientService implements UserDetailsService{
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersTable user = userRepo.findByEmail(email);

        
        if(user != null) {
            var springUser= User.withUsername(user.getEmail()).password(user.getPassword()).build();
            return springUser;
        }
        
        return null;
    }
    
}