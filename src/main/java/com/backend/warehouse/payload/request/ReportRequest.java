package com.backend.warehouse.payload.request;

import java.sql.Date;
import java.util.Set;

import jakarta.validation.constraints.*;

public class ReportRequest {
    private String name;
    private Integer quantity;
    private java.sql.Date checkin;
    private java.sql.Date checkout;
    private String delivery;
    private Long booking_id;
    
	public ReportRequest(String name, Integer quantity, Date checkin, Date checkout, String delivery, Long booking_id) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.checkin = checkin;
		this.checkout = checkout;
		this.delivery = delivery;
		this.booking_id = booking_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public java.sql.Date getCheckin() {
		return checkin;
	}
	public void setCheckin(java.sql.Date checkin) {
		this.checkin = checkin;
	}
	public java.sql.Date getCheckout() {
		return checkout;
	}
	public void setCheckout(java.sql.Date checkout) {
		this.checkout = checkout;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public Long getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(Long booking_id) {
		this.booking_id = booking_id;
	}
	
    
    


}
