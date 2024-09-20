package com.backend.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.ItemRepository;

@Service
public class ItemServiceImpl {
	
	@Autowired
    private ItemRepository itemRepository;
	
    public List<Item> getAllProducts() {
        return itemRepository.findAll();
    }
    
    public void deleteItemsByBookingId(Long bookingId) {
        itemRepository.deleteByBookingId(bookingId);
    }

}
