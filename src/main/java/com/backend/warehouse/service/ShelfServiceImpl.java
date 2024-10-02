package com.backend.warehouse.service;

import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfServiceImpl {

    @Autowired
    private ShelfRepository shelfRepository;

    public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
    }

    public Shelf getShelfById(Long id) {
        Optional<Shelf> shelf = shelfRepository.findById(id);
        return shelf.orElse(null);
    }

    public Shelf createShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public Shelf updateShelf(Long id, Shelf shelfDetails) {
        Optional<Shelf> existingShelf = shelfRepository.findById(id);
        if (existingShelf.isPresent()) {
            Shelf shelf = existingShelf.get();
            shelf.setNameShelf(shelfDetails.getNameShelf());
            shelf.setType(shelfDetails.getType());
            shelf.setPosition(shelfDetails.getPosition());
            shelf.setCompartments(shelfDetails.getCompartments());
            shelf.setWarehouse(shelfDetails.getWarehouse());
            return shelfRepository.save(shelf);
        }
        return null;
    }

    public boolean deleteShelf(Long id) {
        Optional<Shelf> existingShelf = shelfRepository.findById(id);
        if (existingShelf.isPresent()) {
            shelfRepository.delete(existingShelf.get());
            return true;
        }
        return false;
    }
}
