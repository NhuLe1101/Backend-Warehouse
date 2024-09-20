package com.backend.warehouse.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.ItemServiceImpl;


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
