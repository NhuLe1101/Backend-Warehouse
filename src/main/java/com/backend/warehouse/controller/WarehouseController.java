package com.backend.warehouse.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.entity.Warehouse;
import com.backend.warehouse.service.WarehouseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;

	@GetMapping
	public List<Warehouse> getAllWarehouses() {
		return warehouseService.getAllWarehouses();
	}

	@PostMapping
	public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
		Warehouse savedWarehouse = warehouseService.addWarehouse(warehouse);
		return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
		return warehouseService.getWarehouseById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
		Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, warehouse);
		return ResponseEntity.ok(updatedWarehouse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
		warehouseService.deleteWarehouse(id);
		return ResponseEntity.noContent().build();
	}
}
