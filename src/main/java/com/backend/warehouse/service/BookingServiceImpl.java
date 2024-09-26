package com.backend.warehouse.service;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.repository.BookingRepository;

import io.jsonwebtoken.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;
    
    private final String uploadDir = "uploads/";

    public Booking saveData(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    Path uploadPath = Paths.get(uploadDir);
    
	  public void saveFormData(String email, String phoneNumber, String fullName, MultipartFile file) throws IOException, java.io.IOException {
	  
	  if (!Files.exists(uploadPath)) {
	      Files.createDirectories(uploadPath);
	  }
	
	  String originalFileName = file.getOriginalFilename();
	  String fileName = originalFileName;
	  Path filePath = uploadPath.resolve(fileName);
	
	  while (Files.exists(filePath)) {
	      String newFileName = String.format("%s_%s.%s", 
	          originalFileName.substring(0, originalFileName.lastIndexOf(".")), 
	          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")), 
	          originalFileName.substring(originalFileName.lastIndexOf(".") + 1));
	      filePath = uploadPath.resolve(newFileName);
	  }
	
	  Files.copy(file.getInputStream(), filePath);
	
	  Booking booking = new Booking();
	  booking.setCustomerEmail(email);
	  booking.setNumberphone(phoneNumber);
	  booking.setCustomerName(fullName);
	  booking.setExcelFile(filePath.toString());
	  
	  bookingRepository.save(booking);
	  System.out.println("File đã được lưu vào: " + filePath.toString());
	
	}
    
	 public List<Booking> getAllBookings() {
	  return bookingRepository.findAll();
	}
}
