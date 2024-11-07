package com.backend.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Compartment;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	@Transactional
	void deleteByBookingId(Long bookingId);
	
	List<Item> findByNameContainingIgnoreCase(String name);
	
	List<Item> findByStatus(String status);
}
