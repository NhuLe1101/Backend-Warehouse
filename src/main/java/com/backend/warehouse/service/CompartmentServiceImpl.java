package com.backend.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.repository.CompartmentRepository;
import com.backend.warehouse.repository.ShelfRepository;

@Service
public class CompartmentServiceImpl implements CompartmentService {

	@Autowired
	private CompartmentRepository compartmentRepository;

	@Autowired
	private ShelfRepository shelfRepository;

	@Override
	public Compartment saveCompartment(Long shelfId, Compartment compartment) {
	    Shelf shelf = shelfRepository.findById(shelfId)
	        .orElseThrow(() -> new RuntimeException("Shelf not found"));

	    // Kiểm tra nếu trong database compartment đã tồn tại với cùng shelf và layerIndex
	    Optional<Compartment> existingCompartment = compartmentRepository.findByNameCompAndLayerIndexAndShelf(
	        compartment.getNameComp(), 
	        compartment.getLayerIndex(), 
	        shelf
	    );

	    if (existingCompartment.isPresent()) {
	        throw new RuntimeException("Compartment đã tồn tại");
	    }

	    compartment.setShelf(shelf);
	    return compartmentRepository.save(compartment);
	}
}
