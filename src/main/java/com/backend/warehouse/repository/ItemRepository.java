package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
}
