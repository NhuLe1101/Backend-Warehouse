package com.backend.warehouse.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.warehouse.entity.Item;
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

}
