package com.microservice.store.auth.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.store.auth.service.entity.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserName(String userName);

}