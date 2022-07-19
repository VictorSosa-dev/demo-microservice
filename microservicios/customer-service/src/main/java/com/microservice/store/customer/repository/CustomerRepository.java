package com.microservice.store.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.store.customer.entity.Customer;
import com.microservice.store.customer.entity.Region;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Customer findByNumberID(String numberID);

	public List<Customer> findByLastName(String lastName);

	public List<Customer> findByRegion(Region region);
}