package com.microservice.store.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.store.auth.dto.TokenDto;
import com.microservice.store.auth.dto.UserDto;
import com.microservice.store.auth.entity.User;
import com.microservice.store.auth.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	AuthUserService authUserService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto){
		TokenDto tokenDto = authUserService.login(userDto);
		if(tokenDto == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok(tokenDto);
	}

	@PostMapping("/validate")
	public ResponseEntity<TokenDto> validate(@RequestParam String token){
		TokenDto tokenDto = authUserService.validate(token);
		if(tokenDto == null)
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(tokenDto);
	}

	@PostMapping("/singup")
	public ResponseEntity<User> signup(@RequestBody UserDto userDto){
		User user = authUserService.save(userDto);
		
		if(user == null )
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(user);
	}

}
