package com.valkyrie.authentication_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.valkyrie.authentication_service.repository.UserRepository;
import com.valkyrie.authentication_service.model.User;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository repo;
    @Autowired
    private void setRepo(UserRepository repo) {this.repo = repo;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findById(username).orElse(null);

        if (user == null) {throw new UsernameNotFoundException("USER_404");}
        
        return new CustomUserDetails(user);
    }

}
