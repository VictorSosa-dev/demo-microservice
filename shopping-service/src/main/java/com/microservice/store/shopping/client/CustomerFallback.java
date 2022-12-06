package com.microservice.store.shopping.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.microservice.store.shopping.model.Customer;

@Component
public class CustomerFallback implements CustomerClient {

	@Override
	public ResponseEntity<Customer> getCustomer(long id) {
		Customer customer = Customer.builder().firstName("none").lastName("none").email("none").photoUrl("none")
				.build();
		return ResponseEntity.ok(customer);
	}
}