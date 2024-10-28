package com.backend.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.entity.User;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.repository.CheckoutRecordRepository;
import com.backend.warehouse.repository.CompartmentRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.repository.ShelfRepository;
import com.backend.warehouse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CompartmentServiceImpl implements CompartmentService {

	@Autowired
	private CompartmentRepository compartmentRepository;

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CheckoutRecordRepository checkoutRecordRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Compartment> getAllCompartments() {
		return compartmentRepository.findAll();
	}

	@Override
	public Compartment getCompartmentById(Long compartmentId) {
		return compartmentRepository.findById(compartmentId).orElse(null);
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
	public MessageResponse addItemToCompartment(Long compartmentId, Long itemId, int quantity) {
	    Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);

	    if (compartment == null) {
	        return new MessageResponse("Error: Compartment không tồn tại!");
	    }

	    if (compartment.isReserved()) {
	        return new MessageResponse("Error: Ngăn chứa này đang được giữ tạm thời vì sản phẩm trong ngăn này đang trong quá trình xuất hàng.");
	    }

	    Item item = itemRepository.findById(itemId).orElse(null);
	    if (item == null) {
	        return new MessageResponse("Error: Item không tồn tại!");
	    }

	    // Kiểm tra loại của Shelf trong Compartment và loại của Item có khớp nhau không
	    if (!compartment.getShelf().getType().equals(item.getType())) {
	        return new MessageResponse("Error: Type của Item và Shelf không khớp nhau!");
	    }

	    // Tính số lượng còn lại thực sự
	    Integer totalQuantityInCompartments = compartmentRepository.sumQuantityByItemId(itemId);
	    int totalQuantityInComp = (totalQuantityInCompartments != null) ? totalQuantityInCompartments : 0;
	    Integer totalQuantityCheckedOut = checkoutRecordRepository.sumQuantityByItemIdAndConfirmed(itemId, false);
	    int totalCheckedOut = (totalQuantityCheckedOut != null) ? totalQuantityCheckedOut : 0;
	    int remainingQuantity = item.getQuantity() - totalQuantityInComp - totalCheckedOut;

	    if (quantity > remainingQuantity) {
	        return new MessageResponse("Error: Số lượng sản phẩm không đủ. Số lượng còn lại: " + remainingQuantity);
	    }

	    // Đặt reserved = false khi thêm item vào ngăn
	    compartment.setItem(item);
	    compartment.setHasItem(true);
	    compartment.setQuantity(quantity);
	    compartment.setReserved(false);
	    compartmentRepository.save(compartment);

	    return new MessageResponse("Sản phẩm đã được thêm vào ngăn thành công!");
	}

	@Override
	public MessageResponse updateItemQuantity(Long compartmentId, Long itemId, int quantity) {
	    Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);

	    if (compartment == null) {
	        return new MessageResponse("Error: Compartment không tồn tại!");
	    }

	    Item item = itemRepository.findById(itemId).orElse(null);

	    if (item == null) {
	        return new MessageResponse("Error: Item không tồn tại!");
	    }

	    int currentQuantity = compartment.getQuantity();
	    int totalQuantityInCompartments = compartmentRepository.sumQuantityByItemId(itemId);
	    
	    // Tính số lượng còn lại thực tế trước khi loại trừ currentQuantity
	    int remainingQuantity = item.getQuantity() - totalQuantityInCompartments;

	    // Nếu remainingQuantity là 0 hoặc âm, thông báo không còn số lượng khả dụng
	    if (remainingQuantity <= 0) {
	        return new MessageResponse("Error: Số lượng sản phẩm không đủ. Số lượng còn lại: " + remainingQuantity);
	    }

	    // Kiểm tra nếu số lượng yêu cầu lớn hơn remainingQuantity cộng currentQuantity
	    if (quantity > (remainingQuantity + currentQuantity)) {
	        return new MessageResponse("Error: Số lượng sản phẩm không đủ. Số lượng còn lại: " + remainingQuantity);
	    }

	    // Cập nhật số lượng trong compartment
	    compartment.setQuantity(quantity);
	    compartmentRepository.save(compartment);

	    return new MessageResponse("Sản phẩm đã được sửa thành công!");
	}



	@Override
	public MessageResponse removeItemFromCompartment(Long compartmentId, Long itemId) {
	    Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);
	    if (compartment == null) {
	        return new MessageResponse("Error: Compartment không tồn tại!");
	    }

	    Item item = itemRepository.findById(itemId).orElse(null);
	    if (item == null) {
	        return new MessageResponse("Error: Item không tồn tại!");
	    }

	    // Xóa item khỏi ngăn và đặt reserved = false
	    compartment.setItem(null);
	    compartment.setHasItem(false);
	    compartment.setQuantity(0);
	    compartment.setReserved(false);
	    compartmentRepository.save(compartment);

	    return new MessageResponse("Sản phẩm đã được xóa thành công!");
	}


	@Override 
	public MessageResponse checkoutItem(Long compartmentId, Long itemId, String referenceNo, String delivery) {
	    // Lấy thông tin user từ token
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    Long userId = userDetails.getUserid();
	    User user = userRepository.findById(userId).orElse(null);

	    if (user == null) {
	        return new MessageResponse("Error: Nhân viên không tồn tại!");
	    }

	    // Kiểm tra compartment
	    Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);
	    if (compartment == null) {
	        return new MessageResponse("Error: Compartment không tồn tại!");
	    }

	    if (compartment.isReserved()) {
	        return new MessageResponse("Error: Ngăn chứa này đang được giữ tạm thời vì sản phẩm trong ngăn này đang trong quá trình xuất hàng.");
	    }

	    // Kiểm tra item
	    Item item = itemRepository.findById(itemId).orElse(null);
	    if (item == null) {
	        return new MessageResponse("Error: Item không tồn tại!");
	    }

	    // Kiểm tra ngày checkout, referenceNo và delivery
	    LocalDate today = LocalDate.now();
	    if (!today.equals(item.getCheckout())) {
	        return new MessageResponse("Error: Ngày xuất hàng không khớp với ngày hôm nay!");
	    }

	    if (!item.getBooking().getReferenceNo().toString().equals(referenceNo)) {
	        return new MessageResponse("Error: Mã xác nhận xuất hàng không khớp với mã đã đăng ký!");
	    }

	    if (!item.getDelivery().equals(delivery)) {
	        return new MessageResponse("Error: Biển số xe không khớp với mã đã đăng ký!");
	    }

	    // Tính tổng thời gian lưu kho
	    LocalDate checkinDate = item.getCheckin();
	    LocalDate checkoutDate = LocalDate.now();
	    long storageDuration = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
	    
	    // Đánh dấu ngăn chứa là đang được giữ tạm thời
	    compartment.setReserved(true);
	    compartmentRepository.save(compartment);

	    // Tạo và lưu CheckoutRecord
	    CheckoutRecord checkoutRecord = new CheckoutRecord();
	    checkoutRecord.setItem(item);
	    checkoutRecord.setCompartment(compartment);
	    checkoutRecord.setReferenceNo(referenceNo);
	    checkoutRecord.setDelivery(delivery);
	    checkoutRecord.setCheckoutDate(LocalDate.now());
	    checkoutRecord.setConfirmed(false);
	    checkoutRecord.setQuantity(compartment.getQuantity());
	    checkoutRecord.setStorageDuration(storageDuration); 
	    checkoutRecord.setUser(user); // Thiết lập user cho CheckoutRecord

	    checkoutRecordRepository.save(checkoutRecord);

	    // Xóa item khỏi Compartment tạm thời
	    compartment.setItem(null);
	    compartment.setHasItem(false);
	    compartment.setQuantity(0);
	    compartment.setReserved(true); 
	    compartmentRepository.save(compartment);

	    return new MessageResponse("Sản phẩm đã được thêm vào danh sách vận chuyển thành công!");
	}


	@Override
	public List<CheckoutRecord> getPendingCheckoutItems() {
		// Lấy danh sách các CheckoutRecord chưa xác nhận
		return checkoutRecordRepository.findByConfirmedFalse();
	}

	// Hàm xác nhận checkout
	@Override
	public MessageResponse confirmCheckout(Long recordId) {
	    CheckoutRecord record = checkoutRecordRepository.findById(recordId).orElse(null);
	    if (record == null) {
	        return new MessageResponse("Error: Không tìm thấy bản ghi xuất hàng!");
	    }

	    // Xác nhận bản ghi checkout
	    record.setConfirmed(true);
	    checkoutRecordRepository.save(record);

	    // Đặt lại trạng thái "reserved" cho compartment
	    Compartment compartment = record.getCompartment();
	    compartment.setReserved(false);
	    compartmentRepository.save(compartment);

	    // Kiểm tra tổng số lượng đã checkout cho item
	    Item item = record.getItem();
	    if (item != null) {
	        // Tính tổng số lượng đã được xác nhận checkout cho item này
	        int totalConfirmedQuantity = checkoutRecordRepository.sumConfirmedQuantityByItemId(item.getItemId());

	        // Nếu tổng số lượng đã xác nhận bằng với số lượng của item, cập nhật trạng thái thành "Đã xuất kho"
	        if (totalConfirmedQuantity >= item.getQuantity()) {
	            item.setStatus("Đã xuất kho");
	            itemRepository.save(item);
	        }
	    }

	    return new MessageResponse("Xác nhận xuất hàng thành công!");
	}


	// Hàm hủy checkout và trả item lại vào compartment
	@Override
	public MessageResponse cancelCheckout(Long recordId) {
	    CheckoutRecord record = checkoutRecordRepository.findById(recordId).orElse(null);
	    if (record == null) {
	        return new MessageResponse("Error: Không tìm thấy bản ghi xuất hàng!");
	    }

	    Compartment compartment = record.getCompartment();
	    Item item = record.getItem();

	    // Đưa item trở lại compartment và đặt lại reserved = false
	    if (item != null) {
	        compartment.setItem(item);
	        compartment.setHasItem(true);
	        compartment.setQuantity(record.getQuantity());
	        compartment.setReserved(false);
	        compartmentRepository.save(compartment);
	    }

	    checkoutRecordRepository.delete(record);
	    return new MessageResponse("Xuất hàng đã bị hủy và sản phẩm đã được trả lại vào ngăn!");
	}


}
