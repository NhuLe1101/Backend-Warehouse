package com.backend.warehouse.service;

import java.time.LocalDate;
import java.util.List;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.response.ItemResponse;

import io.jsonwebtoken.io.IOException;

public interface ItemService {
	List<ItemResponse> getAllProducts();
	
	List<ItemResponse> searchItem(String name);
	
	List<ItemResponse> getListProducts();
	
	List<Compartment> getCompartmentsByItemId(String itemId);

	Item updateItem(String id, String name, int quantity, String status, LocalDate checkin, LocalDate checkout, String delivery, Float weight) throws IOException;
	
	List<Long> getBookingIdsFromItems();
}
