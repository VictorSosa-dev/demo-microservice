package com.microservice.store.auth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
	
	private String name;
	private String lastName;
	private String username;
	private String email;
	private String password;
}
