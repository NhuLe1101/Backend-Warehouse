package com.backend.warehouse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Booking;

import io.jsonwebtoken.io.IOException;

public interface BookingService {
	void saveFormData(MultipartFile file) throws IOException, java.io.IOException;
	
	List<Booking> getAllBookings();

	Booking updateBooking(Long id, String email, String phoneNumber, String fullName, String filePath);

}
