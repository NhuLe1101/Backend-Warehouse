package com.backend.warehouse.controller;
import java.util.List;
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
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.service.ShelfService;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/shelf")
public class ShelfController {
	@Autowired
	private ShelfService shelfService;
	@GetMapping
	public List<Shelf> getAllShelves() {
		return shelfService.getAllShelves();
	}
	@PostMapping
	public ResponseEntity<Shelf> addShelf(@RequestBody Shelf shelf) {
		Shelf savedShelf = shelfService.addShelf(shelf);
		return new ResponseEntity<>(savedShelf, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Shelf> updateShelf(@PathVariable Long id, @RequestBody Shelf shelf) {
		Shelf updatedShelf = shelfService.updateShelf(id, shelf);
		return ResponseEntity.ok(updatedShelf);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
		shelfService.deleteShelf(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}