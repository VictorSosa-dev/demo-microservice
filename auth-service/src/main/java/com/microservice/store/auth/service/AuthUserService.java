package com.microservice.store.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.store.auth.dto.TokenDto;
import com.microservice.store.auth.dto.UserDto;
import com.microservice.store.auth.entity.User;
import com.microservice.store.auth.repository.UserRepository;
import com.microservice.store.auth.security.JwtProvider;

@Service
public class AuthUserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	public User save(UserDto userDto) {
		Optional<User> user = userRepository.findByEmail(userDto.getEmail());
		
		if(user.isPresent()) return null;
		
		String password = passwordEncoder.encode(userDto.getPassword());
		User authUser = User.builder()
				.name(userDto.getName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.userName(userDto.getUsername())
				.password(password).build();
		
		return userRepository.save(authUser);
	}
	
	public TokenDto login(UserDto userDto) {
		
		Optional<User> user = userRepository.findByEmail(userDto.getEmail());
		if(user.isPresent()) return null;
		
		if(passwordEncoder.matches(userDto.getPassword(), user.get().getPassword()))
			return new TokenDto(jwtProvider.createToken(user.get()));
		
		return null;
	}
	
	public TokenDto validate(String token) {
		if(!jwtProvider.validate(token))
			return null;
		
		String userName = jwtProvider.getUserNameFromToken(token);
		if(!userRepository.findByUserName(userName).isPresent())
			return null;
		
		return new TokenDto(token);
	}
}
