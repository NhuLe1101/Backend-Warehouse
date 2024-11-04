package com.backend.warehouse.payload.request;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.backend.warehouse.entity.CheckoutRecord;

public class DeliveryReport {
	private String checkoutDate;
	private String employeeName;
	private String delivery;
	private String referenceNo;
	private List<CheckoutRecord> items;
	public DeliveryReport(String checkoutDate, String employeeName, String delivery, String referenceNo,
			List<CheckoutRecord> items) {
		super();
		this.checkoutDate = checkoutDate;
		this.employeeName = employeeName;
		this.delivery = delivery;
		this.referenceNo = referenceNo;
		this.items = items;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public List<CheckoutRecord> getItems() {
		return items;
	}
	public void setItems(List<CheckoutRecord> items) {
		this.items = items;
	}
	
	
	
	
}
