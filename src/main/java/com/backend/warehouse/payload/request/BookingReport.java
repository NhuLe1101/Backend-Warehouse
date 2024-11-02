package com.backend.warehouse.payload.request;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.backend.warehouse.entity.CheckoutRecord;

public class BookingReport {
	private String customerEmail;
	private String customerName;
	private String numberphone;
	private Long referenceNo;
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
	public Long getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}
	public BookingReport(String customerEmail, String customerName, String numberphone, Long referenceNo) {
		super();
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.numberphone = numberphone;
		this.referenceNo = referenceNo;
	}
	
	@Override
	public String toString() {
	    return "BookingReport{" +
	            "customerEmail='" + customerEmail + '\'' +
	            ", customerName='" + customerName + '\'' +
	            ", numberphone='" + numberphone + '\'' +
	            ", referenceNo=" + referenceNo +
	            '}';
	}

	
}
