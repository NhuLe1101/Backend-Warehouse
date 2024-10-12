package com.backend.warehouse.service;

import java.time.LocalDate;
import java.util.List;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;

import io.jsonwebtoken.io.IOException;

public interface ItemService {
	List<Item> getAllProducts();
	
	List<Compartment> getCompartmentsByItemId(Long itemId);

	Item updateItem(Long id, String name, int quantity, String status, LocalDate checkin, LocalDate checkout, String delivery, Float weight) throws IOException;

}
