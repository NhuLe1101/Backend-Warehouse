package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.warehouse.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT COUNT(b) FROM Booking b")
    Long countTotalCustomers();
}
