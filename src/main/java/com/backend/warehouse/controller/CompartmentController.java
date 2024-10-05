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
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.entity.Compartment;
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

    @PostMapping("/{shelfId}")
	public ResponseEntity<Compartment> saveCompartment(@PathVariable Long shelfId, @RequestBody Compartment compartment) {
		Compartment savedCompartment = compartmentService.saveCompartment(shelfId, compartment);
		return ResponseEntity.ok(savedCompartment);
	}
}
