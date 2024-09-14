package com.backend.warehouse.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.BookingServiceImpl;
import io.jsonwebtoken.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFormData(
        @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("fullName") String fullName,
        @RequestParam("delivery") String delivery,
        @RequestParam("file") MultipartFile file,
        @RequestParam("status") String status,
        @RequestParam("checkin") LocalDate checkin,
        @RequestParam("checkout") LocalDate checkout
    ) {
        try {
            bookingService.saveFormData(email, phoneNumber, fullName, delivery, file, status, checkin, checkout);
            return ResponseEntity.ok(new MessageResponse("Dữ liệu đã được lưu thành công!"));
        } catch (IOException e) {
            return ResponseEntity
                .status(500) 
                .body(new MessageResponse("Có lỗi xảy ra khi lưu dữ liệu: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(400) 
                .body(new MessageResponse("Dữ liệu không hợp lệ: " + e.getMessage()));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

}
