package com.valkyrie.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valkyrie.authentication_service.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
