package com.backend.warehouse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.payload.response.BookingResponse;

import io.jsonwebtoken.io.IOException;

public interface BookingService {
	void saveFormData(MultipartFile file) throws IOException, java.io.IOException;
	
	List<BookingResponse> getAllBookings();

	Booking updateBooking(String id, String email, String phoneNumber, String fullName, String filePath);

}
