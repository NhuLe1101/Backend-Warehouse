package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Compartment;

public interface CompartmentRepository extends JpaRepository<Compartment, Long> {
	
}
