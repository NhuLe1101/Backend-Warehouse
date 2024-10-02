package com.backend.warehouse.controller;

import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.service.ShelfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
public class ShelfController {

    @Autowired
    private ShelfServiceImpl shelfService;

    @GetMapping
    public List<Shelf> getAllShelves() {
        return shelfService.getAllShelves();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Shelf> getShelfById(@PathVariable Long id) {
        Shelf shelf = shelfService.getShelfById(id);
        if (shelf != null) {
            return ResponseEntity.ok(shelf);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Shelf createShelf(@RequestBody Shelf shelf) {
        return shelfService.createShelf(shelf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shelf> updateShelf(@PathVariable Long id, @RequestBody Shelf shelfDetails) {
        Shelf updatedShelf = shelfService.updateShelf(id, shelfDetails);
        if (updatedShelf != null) {
            return ResponseEntity.ok(updatedShelf);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
        boolean isDeleted = shelfService.deleteShelf(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
