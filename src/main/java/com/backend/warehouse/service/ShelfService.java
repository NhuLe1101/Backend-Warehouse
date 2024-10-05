package com.backend.warehouse.service;

import java.util.List;

import com.backend.warehouse.entity.Shelf;

public interface ShelfService {
	List<Shelf> getAllShelves();

	Shelf addShelf(Shelf shelf);

	Shelf updateShelf(Long id, Shelf shelf);
	
    void deleteShelf(Long id);

}
