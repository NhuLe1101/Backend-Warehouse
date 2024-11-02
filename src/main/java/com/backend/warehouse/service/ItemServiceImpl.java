package com.backend.warehouse.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.service.ItemService;

import io.jsonwebtoken.io.IOException;

@Service
public class ItemServiceImpl implements ItemService{
	
	 @Autowired
	 private ItemRepository itemRepository;

	@Override
    public List<Item> getAllProducts() {
        return itemRepository.findAll();
    }
    
	@Override
    public List<Compartment> getCompartmentsByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item không tồn tại"));

        // Trả về danh sách compartments của item
        return item.getCompartments();
    }
   
	@Override
	public Item updateItem(Long id, String name, int quantity, String status, LocalDate checkin, LocalDate checkout, String delivery, Float weight) throws IOException {
	Item item = itemRepository.findById(id)
	      .orElseThrow(() -> new RuntimeException("Không tìm thấy item với ID: " + id));
	  
	item.setName(name);
	item.setQuantity(quantity);
	item.setStatus(status);
	item.setCheckin(checkin);
	item.setCheckout(checkout);
	item.setDelivery(delivery);
	item.setWeight(quantity);
	
	return itemRepository.save(item);
	}
	
	@Override
	public List<Item> getItemByName(String name) {
	    return itemRepository.findByNameContainingIgnoreCase(name);
	}
	

}
