package com.microservice.store.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;
import com.microservice.store.gateway.dto.TokenDto;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
		
	public static class Config {
		
	}

	private WebClient.Builder webClient;
	
	public AuthFilter(WebClient.Builder webClient) {
		super(Config.class);
		this.webClient = webClient;
	}
	
	public Mono<Void> onError(ServerWebExchange exchage, HttpStatus Status){
		ServerHttpResponse response = exchage.getResponse();
		return response.setComplete();
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (((exchange, chain) -> {
			if(!exchange.getResponse().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, HttpStatus.BAD_REQUEST);
			String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			
			String [] chucks = tokenHeader.split(" ");
			if(chucks.length != 2 || !chucks[0].equals("Bearer"))
				return onError(exchange, HttpStatus.BAD_REQUEST);
			
			return webClient.build()
					.post()
					.uri("http://auth-service/auth/validate?token="+chucks[1])
					.retrieve().bodyToMono(TokenDto.class)
					.map(t -> {	
						t.getToken();
						return exchange;
					}).flatMap(chain::filter);
		}));
	}
}
