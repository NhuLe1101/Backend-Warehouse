package com.backend.warehouse.payload.request;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.backend.warehouse.entity.CheckoutRecord;

public class DeliveryReportPrintTemplate {
	private String productName;
	private int quantity;
	private float weight;
	private String type;
	private String nameComp;
	private String nameShelf;
	private Long storageDuration;
	
	private String employeeName;
	private String delivery;
	private String referenceNo;
	private java.sql.Date checkout;
	public DeliveryReportPrintTemplate(String productName, int quantity, float weight, String type, String nameComp,
			String nameShelf, Long storageDuration, String employeeName, String delivery, String referenceNo,
			Date checkout) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.weight = weight;
		this.type = type;
		this.nameComp = nameComp;
		this.nameShelf = nameShelf;
		this.storageDuration = storageDuration;
		this.employeeName = employeeName;
		this.delivery = delivery;
		this.referenceNo = referenceNo;
		this.checkout = checkout;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNameComp() {
		return nameComp;
	}
	public void setNameComp(String nameComp) {
		this.nameComp = nameComp;
	}
	public String getNameShelf() {
		return nameShelf;
	}
	public void setNameShelf(String nameShelf) {
		this.nameShelf = nameShelf;
	}
	public Long getStorageDuration() {
		return storageDuration;
	}
	public void setStorageDuration(Long storageDuration) {
		this.storageDuration = storageDuration;
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
	public java.sql.Date getCheckout() {
		return checkout;
	}
	public void setCheckout(java.sql.Date checkout) {
		this.checkout = checkout;
	}
	
}
