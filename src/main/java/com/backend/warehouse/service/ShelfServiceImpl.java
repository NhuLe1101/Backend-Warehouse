package com.backend.warehouse.service;

<<<<<<< HEAD
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
=======
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
>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71
    }
}
