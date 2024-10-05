package com.backend.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Warehouse;

public interface WarehouseRepository  extends JpaRepository<Warehouse, Long>{

}
