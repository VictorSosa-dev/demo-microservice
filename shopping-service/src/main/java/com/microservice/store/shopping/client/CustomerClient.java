package com.microservice.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.store.shopping.model.Customer;

@FeignClient(name = "customer-service", fallback = CustomerFallback.class)
public interface CustomerClient {

	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
}