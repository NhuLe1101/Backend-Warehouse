package com.backend.warehouse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.payload.request.AddItemRequest;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.CompartmentService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/compartments")
public class CompartmentController {

	private CompartmentService compartmentService;

	@Autowired
	public CompartmentController(CompartmentService compartmentService) {
		this.compartmentService = compartmentService;
	}

	@GetMapping
	public List<Compartment> getAllCompartments() {
		return compartmentService.getAllCompartments();
	}

	@GetMapping("/{shelfId}/{nameComp}")
	public ResponseEntity<Compartment> getCompartment(@PathVariable Long shelfId, @PathVariable String nameComp) {
		// Gọi service để lấy thông tin compartment từ database
		Compartment compartment = compartmentService.getCompartment(shelfId, nameComp);

		if (compartment != null) {
			return ResponseEntity.ok(compartment); // Trả về 200 OK nếu tìm thấy compartment
		} else {
			return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy compartment
		}
	}

	@PostMapping("/{shelfId}")
	public ResponseEntity<Compartment> saveCompartment(@PathVariable Long shelfId,
			@RequestBody Compartment compartment) {
		Compartment savedCompartment = compartmentService.saveCompartment(shelfId, compartment);
		return ResponseEntity.ok(savedCompartment);
	}

	@PostMapping("/{compartmentId}/addItem")
	public ResponseEntity<?> addItemToCompartment(@PathVariable Long compartmentId,
			@RequestBody AddItemRequest request) {
		MessageResponse response = compartmentService.addItemToCompartment(compartmentId, request.getItemId(),
				request.getQuantity());

		if (response.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

}
