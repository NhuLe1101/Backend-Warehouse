package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String customerEmail;
    
    @Column(nullable = true)
    private String customerName;
    
    @Column(nullable = true)
    private String numberphone;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private LocalDate checkIn;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private LocalDate checkOut;
    
    @Column(nullable = true)
    private String delivery;    
    
    @Column(nullable = false)
    private String excelFile; 
    
    @Column(nullable = true)
    private String status;

    public Booking() {
    	
    }
    
	public Booking(String customerEmail, String customerName, String numberphone, LocalDate checkIn, LocalDate checkOut, String delivery,
			String excelFile, String status) {
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.numberphone = numberphone;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.delivery = delivery;
		this.excelFile = excelFile;
		this.status = status;
	}

	public Long getBookingId() {
		return id;
	}

	public void setBookingId(Long bookingId) {
		this.id = bookingId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNumberphone() {
		return numberphone;
	}

	public void setNumberphone(String numberphone) {
		this.numberphone = numberphone;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public String getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    
}