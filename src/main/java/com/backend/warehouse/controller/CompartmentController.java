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

import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.payload.request.ItemRequest;
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
	public ResponseEntity<?> addItemToCompartment(@PathVariable Long compartmentId, @RequestBody ItemRequest request) {
		MessageResponse response = compartmentService.addItemToCompartment(compartmentId, request.getItemId(),
				request.getQuantity());

		if (response.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{compartmentId}/updateQuantity")
	public ResponseEntity<?> updateItemQuantity(@PathVariable Long compartmentId, @RequestBody ItemRequest request) {
		MessageResponse response = compartmentService.updateItemQuantity(compartmentId, request.getItemId(),
				request.getQuantity());

		if (response.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{compartmentId}/removeItem/{itemId}")
	public ResponseEntity<?> removeItemFromCompartment(@PathVariable Long compartmentId, @PathVariable Long itemId) {

		try {
			compartmentService.removeItemFromCompartment(compartmentId, itemId);
			return ResponseEntity.ok(new MessageResponse("Item đã được xóa khỏi ngăn thành công."));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
		}
	}

	// API checkout item
	@PostMapping("/{compartmentId}/checkout/{itemId}")
	public ResponseEntity<MessageResponse> checkoutItem(@PathVariable Long compartmentId, @PathVariable Long itemId,
			@RequestParam String referenceNo, @RequestParam String delivery) {

		// Gọi service để thực hiện checkout item với userId từ token
		MessageResponse response = compartmentService.checkoutItem(compartmentId, itemId, referenceNo, delivery);

		return response.getMessage().startsWith("Error") ? ResponseEntity.badRequest().body(response)
				: ResponseEntity.ok(response);
	}

	// API lấy danh sách các item đã checkout nhưng chưa xác nhận
	@GetMapping("/checkout/pending")
	public List<CheckoutRecord> getPendingCheckoutItems() {
		return compartmentService.getPendingCheckoutItems();
	}

	// API xác nhận checkout
	@PostMapping("/checkout/confirm/{recordId}")
	public ResponseEntity<MessageResponse> confirmCheckout(@PathVariable Long recordId) {
		MessageResponse response = compartmentService.confirmCheckout(recordId);
		return response.getMessage().startsWith("Error") ? ResponseEntity.badRequest().body(response)
				: ResponseEntity.ok(response);
	}

	// API hủy checkout và trả lại item vào compartment
	@PostMapping("/checkout/cancel/{recordId}")
	public ResponseEntity<MessageResponse> cancelCheckout(@PathVariable Long recordId) {
		MessageResponse response = compartmentService.cancelCheckout(recordId);
		return response.getMessage().startsWith("Error") ? ResponseEntity.badRequest().body(response)
				: ResponseEntity.ok(response);
	}
}
