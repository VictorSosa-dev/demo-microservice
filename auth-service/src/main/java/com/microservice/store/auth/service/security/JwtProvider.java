package com.microservice.store.auth.service.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microservice.store.auth.service.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		
		claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("id",user.getId());
		claims.put("email", user.getEmail());
		Date now = new Date();
		Date exp = new Date(now.getTime() * 3600);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public String getUserNameFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

		} catch (Exception e) {
			// TODO: handle exception
			return "bad token";
		}
	}
	
	public String getEmailFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

		} catch (Exception e) {
			// TODO: handle exception
			return "bad token";
		}
	}
	
}
