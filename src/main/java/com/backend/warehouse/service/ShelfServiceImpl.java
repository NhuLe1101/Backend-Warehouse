package com.backend.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.repository.ShelfRepository;

@Service
public class ShelfServiceImpl implements ShelfService {
	@Autowired
	private ShelfRepository shelfRepository;

	@Override
	public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
	}

	@Override
	public Shelf addShelf(Shelf shelf) {
        return shelfRepository.save(shelf);

	}

	@Override
    public Shelf updateShelf(Long id, Shelf shelf) {
        Optional<Shelf> existingShelfOpt = shelfRepository.findById(id);
        if (existingShelfOpt.isPresent()) {
            Shelf existingShelf = existingShelfOpt.get();
            existingShelf.setNameShelf(shelf.getNameShelf());
            existingShelf.setType(shelf.getType());
            existingShelf.setPosition(shelf.getPosition());
            existingShelf.setCompartments(shelf.getCompartments());
            existingShelf.setWarehouse(shelf.getWarehouse());
            return shelfRepository.save(existingShelf);
        } else {
            throw new RuntimeException("Shelf not found with id: " + id);
        }
    }
    @Override
    public void deleteShelf(Long id) {
        shelfRepository.deleteById(id);
    }
}
