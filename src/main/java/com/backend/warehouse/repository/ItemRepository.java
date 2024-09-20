package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.warehouse.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	@Transactional
	void deleteByBookingId(Long bookingId);
}
