package com.valkyrie.api_gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valkyrie.api_gateway.model.User;

@FeignClient(value = "AUTHENTICATION-SERVICE", configuration = AuthenticationByPass.class)
public interface AuthenticationFeignController {

    @GetMapping("/user/get-user")
    ResponseEntity<User> getUser(@RequestParam String username);
}
