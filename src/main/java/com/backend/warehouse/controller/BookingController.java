package com.backend.warehouse.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.User;
import com.backend.warehouse.payload.request.LoginRequest;
import com.backend.warehouse.payload.request.SignupRequest;
import com.backend.warehouse.payload.response.JwtResponse;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.repository.UserRepository;
import com.backend.warehouse.security.jwt.JwtUtils;
import com.backend.warehouse.service.BookingServiceImpl;
import com.backend.warehouse.service.UserDetailsImpl;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    @PostMapping("/upload")
    public String uploadFormData(
        @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("fullName") String fullName,
        @RequestParam("delivery") String delivery,
        @RequestParam("file") MultipartFile file,
        @RequestParam("status") String status,
        @RequestParam("checkin") LocalDate checkin,
        @RequestParam("checkout") LocalDate checkout
    ) throws java.io.IOException {
        try {
            bookingService.saveFormData(email, phoneNumber, fullName, delivery, file, status, checkin, checkout);
            return "Dữ liệu đã được lưu thành công!";
        } catch (IOException e) {
            return "Có lỗi xảy ra khi lưu dữ liệu: " + e.getMessage();
        }
    }
}
