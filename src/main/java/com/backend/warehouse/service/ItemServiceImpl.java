package com.backend.warehouse.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.response.BookingResponse;
import com.backend.warehouse.payload.response.ItemResponse;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.service.ItemService;

import io.jsonwebtoken.io.IOException;

@Service
public class ItemServiceImpl implements ItemService{
	
	 @Autowired
	 private ItemRepository itemRepository;

		public String formatId(Long id) {
		    if (id < 10) {
		        return "SP000" + id;
		    } else if (id < 100) {
		        return "SP00" + id;
		    } else if (id < 1000) {
		        return "SP0" + id;
		    } else {
		        return "SP" + id;
		    }
		}
		
		public Long parseId(String formattedId) {
		    if (formattedId.startsWith("SP")) {
		        String numericPart = formattedId.substring(2); 
		        return Long.parseLong(numericPart);
		    } else {
		        throw new IllegalArgumentException("Invalid formatted ID: " + formattedId);
		    }
		}

	 
	@Override
    public List<ItemResponse> getAllProducts() {
		List<Item> items = itemRepository.findAll();
		List<ItemResponse> itemDTOs = items.stream().map(item -> 
        new ItemResponse(
        	formatId(item.getItemId()), 
            item.getName(),
            item.getQuantity(),
            item.getWeight(),
            item.getType(),
            item.getCompartments(),
            item.getBooking(),
            item.getCheckin(),
            item.getCheckout(),
            item.getStatus(),
            item.getImage(),
            item.getDelivery()
        )
    ).toList();
		return itemDTOs;
    }
	
	@Override
	public List<ItemResponse> getListProducts() {
		List<Item> items = itemRepository.findByStatus("Đang lưu kho");
		List<ItemResponse> itemDTOs = items.stream().map(item -> 
        new ItemResponse(
        	formatId(item.getItemId()), 
            item.getName(),
            item.getQuantity(),
            item.getWeight(),
            item.getType(),
            item.getCompartments(),
            item.getBooking(),
            item.getCheckin(),
            item.getCheckout(),
            item.getStatus(),
            item.getImage(),
            item.getDelivery()
        )
    ).toList();
		return itemDTOs;
    }
	
	@Override
    public List<Compartment> getCompartmentsByItemId(String itemId) {
        Item item = itemRepository.findById(parseId(itemId))
            .orElseThrow(() -> new RuntimeException("Item không tồn tại"));

        // Trả về danh sách compartments của item
        return item.getCompartments();
    }
   
	@Override
	public Item updateItem(String id, String name, int quantity, String status, LocalDate checkin, LocalDate checkout, String delivery, Float weight) throws IOException {
	Item item = itemRepository.findById(parseId(id))
	      .orElseThrow(() -> new RuntimeException("Không tìm thấy item với ID: " + id));
	  
	item.setName(name);
	item.setQuantity(quantity);
	item.setStatus(status);
	item.setCheckin(checkin);
	item.setCheckout(checkout);
	item.setDelivery(delivery);
	item.setWeight(weight);
	
	return itemRepository.save(item);
	}
	
//	@Override
//	public List<ItemResponse> getItemByName(String name) {
//		List<Item> items = itemRepository.findByNameContainingIgnoreCase(name);
//		List<ItemResponse> itemDTOs = items.stream().map(item -> 
//        new ItemResponse(
//        	formatId(item.getItemId()), 
//            item.getName(),
//            item.getQuantity(),
//            item.getWeight(),
//            item.getType(),
//            item.getCompartments(),
//            item.getBooking(),
//            item.getCheckin(),
//            item.getCheckout(),
//            item.getStatus(),
//            item.getImage(),
//            item.getDelivery()
//        )
//    ).toList();
//		return itemDTOs;
//	}
	
	@Override
	public List<ItemResponse> searchItem(String input) {
	    List<ItemResponse> itemDTOs;

	    try {
	        Long itemId;
	        if (input.matches("\\d+")) { 
	            itemId = Long.parseLong(input);
	        } else if (input.startsWith("SP")) {
	            itemId = parseId(input);
	        } else {
	            return searchByName(input);
	        }

	        Item item = itemRepository.findById(itemId).orElse(null);

	        if (item != null) {
	            itemDTOs = List.of(new ItemResponse(
	                formatId(item.getItemId()), 
	                item.getName(),
	                item.getQuantity(),
	                item.getWeight(),
	                item.getType(),
	                item.getCompartments(),
	                item.getBooking(),
	                item.getCheckin(),
	                item.getCheckout(),
	                item.getStatus(),
	                item.getImage(),
	                item.getDelivery()
	            ));
	        } else {
	            itemDTOs = new ArrayList<>();
	        }

	    } catch (IllegalArgumentException e) {
	        itemDTOs = searchByName(input);
	    }

	    return itemDTOs;
	}

	private List<ItemResponse> searchByName(String name) {
	    List<Item> items = itemRepository.findByNameContainingIgnoreCase(name);
	    return items.stream().map(item -> 
	        new ItemResponse(
	            formatId(item.getItemId()), 
	            item.getName(),
	            item.getQuantity(),
	            item.getWeight(),
	            item.getType(),
	            item.getCompartments(),
	            item.getBooking(),
	            item.getCheckin(),
	            item.getCheckout(),
	            item.getStatus(),
	            item.getImage(),
	            item.getDelivery()
	        )
	    ).toList();
	}

	
	
	public Long getTotalItemsInStock() {
        Long totalItems = itemRepository.countItemsInStock();
        return totalItems != null ? totalItems : 0;
    }
	
	public List<Map<String, Object>> getMonthlyItemCount() {
        List<Object[]> rawData = itemRepository.getMonthlyItemQuantity();
        List<Map<String, Object>> monthlyItemCounts = new ArrayList<>();

        for (Object[] entry : rawData) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", entry[0]);  // Tháng
            monthData.put("totalQuantity", entry[1]);  // Tổng số lượng
            monthlyItemCounts.add(monthData);
        }

        return monthlyItemCounts;
    }
	
	 public void deleteItemsByBookingId(Long bookingId) {
	        itemRepository.deleteByBookingId(bookingId);
	    }
	 
	 
	 @Override
	 public List<Long> getBookingIdsFromItems() {
	     List<Item> items = itemRepository.findByStatusNot("Đã xuất kho");

	     List<Long> bookingIds = items.stream()
	             .map(item -> item.getBooking() != null ? item.getBooking().getId() : null)
	             .filter(bookingId -> bookingId != null)
	             .distinct() 
	             .collect(Collectors.toList());

	     return bookingIds;
	 }

	
}
