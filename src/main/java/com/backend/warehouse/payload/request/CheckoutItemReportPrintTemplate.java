package com.backend.warehouse.payload.request;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.backend.warehouse.entity.CheckoutRecord;

public class CheckoutItemReportPrintTemplate {
	private String productName;
	private int quantity;
	private float weight;
	private String type;
	private String nameComp;
	private String nameShelf;
	private Long storageDuration;
	private java.sql.Date checkout;
	private String delivery;
	private String referenceNo;
	private String profileName;
	public CheckoutItemReportPrintTemplate(String productName, int quantity, float weight, String type, String nameComp,
			String nameShelf, Long storageDuration, Date checkout, String delivery, String referenceNo,
			String profileName) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.weight = weight;
		this.type = type;
		this.nameComp = nameComp;
		this.nameShelf = nameShelf;
		this.storageDuration = storageDuration;
		this.checkout = checkout;
		this.delivery = delivery;
		this.referenceNo = referenceNo;
		this.profileName = profileName;
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
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	
}
