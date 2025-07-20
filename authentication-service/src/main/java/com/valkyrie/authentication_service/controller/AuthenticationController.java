package com.valkyrie.authentication_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.authentication_service.service.UserService;
import com.valkyrie.authentication_service.model.User;
import com.valkyrie.authentication_service.model.Store;

@RestController
@RequestMapping("/user")
public class AuthenticationController {
    private UserService service;
    @Autowired
    private void setService(UserService service) {this.service = service;}

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        Store<String> store = service.signIn(user);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@RequestBody User user) {
        Store<String> store = service.logIn(user);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/get-user")
    public ResponseEntity<String> getUser(@RequestParam String username) {
        Store<String> store = service.getUser(username);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
