package com.backend.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.repository.CompartmentRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.repository.ShelfRepository;

@Service
public class CompartmentServiceImpl implements CompartmentService {

	@Autowired
	private CompartmentRepository compartmentRepository;

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public List<Compartment> getAllCompartments() {
		return compartmentRepository.findAll();
	}

	@Override
	public Compartment getCompartment(Long shelfId, String nameComp) {
		// Tìm kiếm Shelf trước
		Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() -> new RuntimeException("Shelf không tồn tại"));

		// Sau đó tìm kiếm Compartment dựa trên Shelf và nameComp
		return compartmentRepository.findByShelfAndNameComp(shelf, nameComp);
	}

	@Override
	public Compartment saveCompartment(Long shelfId, Compartment compartment) {
		Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() -> new RuntimeException("Shelf not found"));

		// Kiểm tra nếu compartment đã tồn tại
		Optional<Compartment> existingCompartment = compartmentRepository
				.findByNameCompAndLayerIndexAndShelf(compartment.getNameComp(), compartment.getLayerIndex(), shelf);

		if (existingCompartment.isPresent()) {
			throw new RuntimeException("Compartment đã tồn tại");
		}

		compartment.setShelf(shelf);
		return compartmentRepository.save(compartment);
	}

	@Override
	public void addItemToCompartment(Long compartmentId, Long itemId, int quantity) {
	    // Lấy compartment từ DB
	    Compartment compartment = compartmentRepository.findById(compartmentId)
	            .orElseThrow(() -> new RuntimeException("Compartment không tồn tại"));

	    // Lấy item từ DB
	    Item item = itemRepository.findById(itemId)
	            .orElseThrow(() -> new RuntimeException("Item không tồn tại"));

	    // Kiểm tra loại của Shelf trong Compartment và loại của Item có khớp nhau không
	    if (!compartment.getShelf().getType().equals(item.getType())) {
	        throw new RuntimeException("Type của Item và Shelf không khớp nhau");
	    }

	    // Kiểm tra số lượng item
	    if (item.getQuantity() < 1 || quantity < 1) {
	        throw new RuntimeException("Số lượng không hợp lệ. Số lượng phải lớn hơn 0.");
	    }

	    // Kiểm tra số lượng còn lại của item có đủ không
	    if (quantity > item.getQuantity()) {
	        throw new RuntimeException("Số lượng item không đủ");
	    }

	    // Cập nhật số lượng item
	    item.setQuantity(item.getQuantity() - quantity);

	    // Gán item vào compartment
	    compartment.setItem(item);
	    compartment.setHasItem(true);
	    compartment.setQuantity(quantity);

	    // Lưu cập nhật vào database
	    compartmentRepository.save(compartment);
	    itemRepository.save(item);  // Lưu item sau khi cập nhật shelf
	}



}
