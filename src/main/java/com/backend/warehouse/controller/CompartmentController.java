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
	public ResponseEntity<?> addItemToCompartment(@PathVariable Long compartmentId, @RequestBody AddItemRequest request) {
	    try {
	        System.out.println("Received compartmentId: " + compartmentId);
	        System.out.println("Received itemId: " + request.getItemId());
	        System.out.println("Received quantity: " + request.getQuantity());

	        if (request.getItemId() == null || request.getQuantity() == null || request.getQuantity() <= 0) {
	            return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ. Vui lòng kiểm tra itemId hoặc quantity.");
	        }

	        // Gọi service để thêm item vào compartment
	        compartmentService.addItemToCompartment(compartmentId, request.getItemId(), request.getQuantity());
	        return ResponseEntity.ok("Item đã được thêm vào compartment.");
	    } catch (Exception e) {
	        e.printStackTrace();  // Thêm dòng này để ghi chi tiết lỗi ra console
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm item: " + e.getMessage());
	    }
	}



}
