package com.microservice.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservice.store.shopping.model.Customer;

@FeignClient(name = "customer-service", url = "http://localhost:8092")
@RequestMapping("/customers")
public interface CustomerClient {

	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
}
