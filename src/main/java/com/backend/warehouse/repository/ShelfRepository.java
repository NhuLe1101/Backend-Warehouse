package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {

}
