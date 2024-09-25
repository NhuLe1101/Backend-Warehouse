package com.backend.warehouse.service;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.repository.BookingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveData(Booking booking) {
        return bookingRepository.save(booking);
    }
    
	 public List<Booking> getAllBookings() {
	  return bookingRepository.findAll();
	}
}
