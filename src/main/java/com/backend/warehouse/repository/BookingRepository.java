package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
}
