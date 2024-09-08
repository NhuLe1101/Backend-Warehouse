package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = true)
    private String customerName;
    
    @Column(nullable = true)
    private String numberphone;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date checkIn;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date checkOut;
    
    @Column(nullable = true)
    private String delivery;    
    
    @Column(nullable = false)
    private String excelFile; // Tên hoặc đường dẫn file Excel
    
    @Column(nullable = true)
    private String status;

    public Booking() {
    	
    }
    
	public Booking(String customerName, String numberphone, Date checkIn, Date checkOut, String delivery,
			String excelFile, String status) {
		this.customerName = customerName;
		this.numberphone = numberphone;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.delivery = delivery;
		this.excelFile = excelFile;
		this.status = status;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
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

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
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