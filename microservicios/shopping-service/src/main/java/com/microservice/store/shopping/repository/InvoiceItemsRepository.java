package com.microservice.store.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.store.shopping.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem, Long> {
	
}