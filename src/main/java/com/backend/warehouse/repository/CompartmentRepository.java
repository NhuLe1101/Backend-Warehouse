package com.backend.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;


public interface CompartmentRepository extends JpaRepository<Compartment, Long> {
    Optional<Compartment> findByNameCompAndLayerIndexAndShelf(String nameComp, int layerIndex, Shelf shelf);

}

