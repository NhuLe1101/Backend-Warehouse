package com.backend.warehouse.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;


public interface CompartmentRepository extends JpaRepository<Compartment, Long> {
    Optional<Compartment> findByNameCompAndLayerIndexAndShelf(String nameComp, int layerIndex, Shelf shelf);

}
=======
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.Compartment;

public interface CompartmentRepository extends JpaRepository<Compartment, Long> {
	
}
>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71
