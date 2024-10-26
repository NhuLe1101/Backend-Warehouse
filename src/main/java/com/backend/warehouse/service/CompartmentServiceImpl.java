package com.backend.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

	    Item item = itemRepository.findById(itemId).orElse(null);

	    if (item == null) {
	        return new MessageResponse("Error: Item không tồn tại!");
	    }

	    // Kiểm tra loại của Shelf trong Compartment và loại của Item có khớp nhau không
	    if (!compartment.getShelf().getType().equals(item.getType())) {
	        return new MessageResponse("Error: Type của Item và Shelf không khớp nhau!");
	    }

	    // Kiểm tra số lượng item
	    if (item.getQuantity() < 1 || quantity < 1) {
	        return new MessageResponse("Error: Số lượng không hợp lệ. Số lượng phải lớn hơn 0.");
	    }

	    // Gán giá trị mặc định là 0 nếu sumQuantityByItemId trả về null
	    Integer totalQuantityInCompartments = compartmentRepository.sumQuantityByItemId(itemId);
	    int totalQuantity = (totalQuantityInCompartments != null) ? totalQuantityInCompartments : 0;

	    int remainingQuantity = item.getQuantity() - totalQuantity;

	    if (quantity > remainingQuantity) {
	        return new MessageResponse("Error: Số lượng sản phẩm không đủ. Số lượng còn lại: " + remainingQuantity);
	    }

	    compartment.setItem(item);
	    compartment.setHasItem(true);
	    compartment.setQuantity(quantity);

	    // Lưu cập nhật vào database
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

		// Kiểm tra nếu số lượng item còn lại đủ để cập nhật
		int totalQuantityInCompartments = compartmentRepository.sumQuantityByItemId(itemId);
		int remainingQuantity = item.getQuantity() - totalQuantityInCompartments;

		if (quantity > remainingQuantity) {
			return new MessageResponse("Error: Số lượng sản phẩm không đủ. Số lượng còn lại: " + remainingQuantity);
		}

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

		// Xóa item khỏi ngăn
		compartment.setItem(null);
		compartment.setHasItem(false);
		compartment.setQuantity(0);

		// Lưu cập nhật vào database
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

        // Tạo và lưu CheckoutRecord với trạng thái chưa xác nhận và thông tin nhân viên
        CheckoutRecord checkoutRecord = new CheckoutRecord();
        checkoutRecord.setItem(item);
        checkoutRecord.setCompartment(compartment);
        checkoutRecord.setReferenceNo(referenceNo);
        checkoutRecord.setDelivery(delivery);
        checkoutRecord.setCheckoutDate(today);
        checkoutRecord.setConfirmed(false);
        checkoutRecord.setUser(user); // Gán user thực hiện checkout
        checkoutRecordRepository.save(checkoutRecord);

        // Tạm thời xóa item khỏi compartment
        compartment.setItem(null);
        compartment.setHasItem(false);
        compartment.setQuantity(0);
        compartmentRepository.save(compartment);

        return new MessageResponse("Item đã được thêm vào danh sách vận chuyển thành công!");
    }

	@Override
	public List<CheckoutRecord> getPendingCheckoutItems() {
		// Lấy danh sách các CheckoutRecord chưa xác nhận
		return checkoutRecordRepository.findByConfirmedFalse();
	}

	// Hàm xác nhận checkout
	public MessageResponse confirmCheckout(Long recordId) {
	    CheckoutRecord record = checkoutRecordRepository.findById(recordId).orElse(null);
	    if (record == null) {
	        return new MessageResponse("Error: Không tìm thấy bản ghi xuất hàng!");
	    }

	    // Cập nhật trạng thái của CheckoutRecord
	    record.setConfirmed(true);
	    checkoutRecordRepository.save(record);

	    // Cập nhật trạng thái của Item
	    Item item = record.getItem();
	    if (item != null) {
	        item.setStatus("Đã xuất kho"); // Cập nhật trạng thái của item
	        itemRepository.save(item); // Lưu lại trạng thái mới của item
	    }

	    return new MessageResponse("Xác nhận xuất hàng thành công!");
	}

	// Hàm hủy checkout và trả item lại vào compartment
	public MessageResponse cancelCheckout(Long recordId) {
		CheckoutRecord record = checkoutRecordRepository.findById(recordId).orElse(null);
		if (record == null) {
			return new MessageResponse("Error: Không tìm thấy bản ghi xuất hàng!");
		}

		// Lấy lại thông tin compartment từ record và đưa item trở lại compartment
		Compartment compartment = record.getCompartment();
		Item item = record.getItem();
		compartment.setItem(item);
		compartment.setHasItem(true);
		compartment.setQuantity(item.getQuantity()); // Hoặc một lượng thích hợp tùy vào dữ liệu

		compartmentRepository.save(compartment);

		// Xóa bản ghi checkout sau khi hủy
		checkoutRecordRepository.delete(record);

		return new MessageResponse("Xuất hàng đã bị hủy và sản phẩm đã được trả lại vào ngăn!");
	}
}
