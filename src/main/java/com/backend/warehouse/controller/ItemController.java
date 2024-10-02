package com.backend.warehouse.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.ItemServiceImpl;

import io.jsonwebtoken.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllBookings() {
        List<Item> items = itemService.getAllProducts();
        return ResponseEntity.ok(items);
    }
    

    @PutMapping("/{itemId}")
    public ResponseEntity<?> editItem(
            @PathVariable("itemId") String id,
            @RequestParam("bookingid") String bookingid,
            @RequestParam("name") String name,
            @RequestParam("quantity") String quantity,
            @RequestParam("weight") String weight,
            @RequestParam("shelf") String shelf,
            @RequestParam("type") String type,
            @RequestParam("status") String status,
            @RequestParam("checkin") LocalDate checkin,
            @RequestParam("checkout") LocalDate checkout,
            @RequestParam("delivery") String delivery,
            @RequestParam("compartmentsid") List<String> compartmentsIds, // Nhiều compartmentsId
            @RequestParam("compartmentsQuantityItem") List<String> compartmentsQuantityItems // Nhiều quantities
    ) {
        try {
            // Chuyển đổi các tham số sang kiểu dữ liệu tương ứng
            Long itemId = Long.parseLong(id);
            Long bookingId = Long.parseLong(bookingid);
            int itemQuantity = Integer.parseInt(quantity);
            float itemWeight = Float.parseFloat(weight);
            Long shelfId = Long.parseLong(shelf);

            // Chuyển đổi danh sách compartmentsId và compartmentsQuantityItem thành List<List<Long>>
            List<List<Long>> compartmentsQuantityList = new ArrayList<>();
            for (int i = 0; i < compartmentsIds.size(); i++) {
                Long compartmentId = Long.parseLong(compartmentsIds.get(i));
                Long compartmentQuantityItem = Long.parseLong(compartmentsQuantityItems.get(i));
                compartmentsQuantityList.add(Arrays.asList(compartmentId, compartmentQuantityItem));
            }

            // Gọi service với các tham số đã chuyển đổi
            itemService.editItem(itemId, bookingId, name, itemQuantity, itemWeight, shelfId, type, status, checkin, checkout, delivery, compartmentsQuantityList);

            return ResponseEntity.ok(new MessageResponse("Dữ liệu đã được lưu thành công!"));
        } catch (NumberFormatException e) {
            return ResponseEntity
                .status(400)
                .body(new MessageResponse("Dữ liệu không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body(new MessageResponse("Có lỗi xảy ra khi lưu dữ liệu: " + e.getMessage()));
        }
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItemsOfBooking(@PathVariable("id") Long id) {
        try {
        	itemService.deleteItemsByBookingId(id);
            return ResponseEntity.ok(new MessageResponse("Xóa tất cả sản phẩn trong booking thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Có lỗi xảy ra khi xóa sản phẩm!"));
        }
    }

}
