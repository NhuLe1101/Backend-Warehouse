package com.backend.warehouse.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.response.ItemResponse;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.ItemServiceImpl;

import com.backend.warehouse.service.CompartmentService;

import io.jsonwebtoken.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;
    
    @Autowired
    private CompartmentService compartmentService;
    
    @GetMapping("/all")
    public ResponseEntity<List<ItemResponse>> getAllBookings() {
        List<ItemResponse> items = itemService.getAllProducts();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/byStatus")
    public ResponseEntity<List<ItemResponse>> getProductsByStatus() {
        List<ItemResponse> items = itemService.getItemsByStatus();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{itemId}/compartments")
    public ResponseEntity<List<Compartment>> getCompartmentsByItemId(@PathVariable Long itemId) {
        List<Compartment> compartments = itemService.getCompartmentsByItemId(itemId);
        if (compartments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dữ liệu
        }
        return ResponseEntity.ok(compartments); // Trả về danh sách compartments
    }
    
    @GetMapping("/totalItemsInStock")
    public ResponseEntity<Long> getTotalItemsInStock() {
        Long totalItemsInStock = itemService.getTotalItemsInStock();
        return ResponseEntity.ok(totalItemsInStock);
    }
    
    @PutMapping("/update/{id}")
	public ResponseEntity<?> updateItem(
      @PathVariable("id") String id,
      @RequestParam("name") String name,
      @RequestParam("quantity") int quantity,
      @RequestParam("status") String status,
      @RequestParam("checkin") LocalDate checkin,
      @RequestParam("checkout") LocalDate checkout,
      @RequestParam("delivery") String delivery,
      @RequestParam("weight") Float weight
	) {
      try {
          itemService.updateItem(id, name, quantity, status, checkin, checkout, delivery, weight);
          return ResponseEntity.ok(new MessageResponse("Cập nhật booking thành công!"));
      } catch (IOException e) {
          return ResponseEntity
              .status(500) 
              .body(new MessageResponse("Có lỗi xảy ra khi cập nhật dữ liệu: " + e.getMessage()));
      } catch (Exception e) {
          return ResponseEntity
              .status(400) 
              .body(new MessageResponse("Dữ liệu không hợp lệ: " + e.getMessage()));
      }
  }
    
    @GetMapping("/search")
	public ResponseEntity<List<Item>> searchItemsByName(
      @RequestParam String name
	) {
    	  List<Item> items = itemService.getItemByName(name);
    	  return ResponseEntity.ok(items);
  }
    
//    @GetMapping("/compartment-is-null")
//	public ResponseEntity<List<Item>> searchItemsByCompartment(
//	) {
//    	  List<Item> items = itemService.getItemByCompartment();
//    	  return ResponseEntity.ok(items);
//  }
    
    @GetMapping("/items-not-in-compartments")
    public List<ItemResponse> getItemsNotInCompartments() {
        List<Compartment> compartments = compartmentService.getAllCompartments();
        Set<Long> itemIdsSet = new HashSet<>();

        for (Compartment compartment : compartments) {
            if (compartment.isHasItem() && compartment.getItem() != null) {
                itemIdsSet.add(compartment.getItem().getItemId());
            }
        }

        List<ItemResponse> allItems = itemService.getAllProducts();

        List<ItemResponse> filteredItems = allItems.stream()
            .filter(item -> !itemIdsSet.contains(item.getItemId()))
            .collect(Collectors.toList());

        return filteredItems;
    }
    
    @GetMapping("/items-check-in-decrease")
    public List<ItemResponse> getItemsByCheckinDecrease() {
        List<ItemResponse> allItems = itemService.getAllProducts();

        List<ItemResponse> sortedItems = allItems.stream()
            .sorted(Comparator.comparing(ItemResponse::getCheckin).reversed())
            .collect(Collectors.toList());

        return sortedItems;
    }
    
    @GetMapping("/items-check-in-increase")
    public List<ItemResponse> getItemsByCheckinIncrease() {
        List<ItemResponse> allItems = itemService.getAllProducts();

        List<ItemResponse> sortedItems = allItems.stream()
            .sorted(Comparator.comparing(ItemResponse::getCheckin))
            .collect(Collectors.toList());

        return sortedItems;
    }

    
    @GetMapping("/items-check-out-increase")
    public List<ItemResponse> getItemsByCheckoutIncrease() {
        List<ItemResponse> allItems = itemService.getAllProducts();

        List<ItemResponse> sortedItems = allItems.stream()
            .sorted(Comparator.comparing(ItemResponse::getCheckout))
            .collect(Collectors.toList());

        return sortedItems;
    }

    @GetMapping("/items-check-out-decrease")
    public List<ItemResponse> getItemsByCheckoutDecrease() {
        List<ItemResponse> allItems = itemService.getAllProducts();

        List<ItemResponse> sortedItems = allItems.stream()
            .sorted(Comparator.comparing(ItemResponse::getCheckout).reversed())
            .collect(Collectors.toList());

        return sortedItems;
    }

    @GetMapping("/monthlyItemCount")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyItemCount() {
        List<Map<String, Object>> monthlyCounts = itemService.getMonthlyItemCount();
        return ResponseEntity.ok(monthlyCounts);
    }

}
