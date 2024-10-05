package com.backend.warehouse.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.repository.BookingRepository;
import com.backend.warehouse.repository.CompartmentRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.repository.ShelfRepository;

@Service
public class ItemServiceImpl {
	
	 @Autowired
	 private ItemRepository itemRepository;

	 @Autowired
	 private ShelfRepository shelfRepository;

	 @Autowired
	 private CompartmentRepository compartmentRepository;

	 @Autowired
	 private BookingRepository bookingRepository;
	
    public List<Item> getAllProducts() {
        return itemRepository.findAll();
    }
    
    public Item editItem(Long itemId, Long bookingId, String name, int quantity, float weight, Long shelfId,
            String type, String status, LocalDate checkin, LocalDate checkout, String delivery,
            List<List<Long>> compartmentsQuantityItems) throws Exception {
			
			// Tìm item theo id
			Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new Exception("Không tìm thấy Item với ID: " + itemId));
			
			// Tìm booking theo id
			Booking booking = bookingRepository.findById(bookingId)
			.orElseThrow(() -> new Exception("Không tìm thấy Booking với ID: " + bookingId));
			
			// Tìm shelf theo id
			Shelf shelf = shelfRepository.findById(shelfId)
			.orElseThrow(() -> new Exception("Không tìm thấy Shelf với ID: " + shelfId));
			
			// Cập nhật các thuộc tính cơ bản của item
			item.setName(name);
			item.setQuantity(quantity);
			item.setWeight(weight);
			item.setType(type);
			item.setStatus(status);
			item.setCheckin(checkin);
			item.setCheckout(checkout);
			item.setDelivery(delivery);
			item.setShelf(shelf);
			item.setBooking(booking);
			
			// Xử lý các compartments và quantity tương ứng
			List<Compartment> updatedCompartments = new ArrayList<>();
			for (List<Long> compartmentData : compartmentsQuantityItems) {
			Long compartmentId = compartmentData.get(0);
			Long quantityItem = compartmentData.get(1);
			
			// Tìm compartment theo id
			Compartment compartment = compartmentRepository.findById(compartmentId)
			   .orElseThrow(() -> new Exception("Không tìm thấy Compartment với ID: " + compartmentId));
			
			// Set quantity cho compartment
//			compartment.setItemQuantity(quantityItem);
			
			// Thêm compartment đã cập nhật vào danh sách
			updatedCompartments.add(compartment);
			}
			
			// Cập nhật compartments cho item
//			item.setCompartments(updatedCompartments);
			
			// Lưu item đã chỉnh sửa vào database
			return itemRepository.save(item);
			}

    
    public void deleteItemsByBookingId(Long bookingId) {
        itemRepository.deleteByBookingId(bookingId);
    }

}
