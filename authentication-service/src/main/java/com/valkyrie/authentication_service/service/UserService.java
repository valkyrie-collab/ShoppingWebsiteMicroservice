package com.valkyrie.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valkyrie.authentication_service.repository.UserRepository;
import com.valkyrie.authentication_service.model.User;
import com.valkyrie.authentication_service.model.Store;
import com.valkyrie.authentication_service.config.TokenConfig;

@Service
public class UserService {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
    private UserRepository repo;
    @Autowired
    private void setRepo(UserRepository repo) {this.repo = repo;}

    private AuthenticationManager authenticationManager;
    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    public Store<String> signIn(User user) {
        user = user.setPassword(ENCODER.encode(user.getPassword()));
        repo.save(user);
        return repo.findById(user.getUsername()).orElse(null) == null?
                Store.initialize(HttpStatus.BAD_REQUEST, "User not signed in....") : 
                Store.initialize(HttpStatus.ACCEPTED, "User sign in successful...");
    }

    public Store<String> logIn(User user) {
        String token = null;
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername());
            return Store.initialize(HttpStatus.OK, token);
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Token not generated...");
    }

    public Store<User> getUser(String username) {
        User user = repo.findById(username).orElse(null);
        return user == null? Store.initialize(HttpStatus.BAD_REQUEST, null) :
                Store.initialize(HttpStatus.OK, user);
    }
}
