package com.backend.warehouse.payload.response;

import java.time.LocalDate;
import java.util.List;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.Compartment;

public class ItemResponse {
	 private String itemId; 
	    private String name;
	    private int quantity;
	    private float weight;
	    private String type;
	    private List<Compartment> compartments; 
	    private Booking booking;
	    private LocalDate checkin;
	    private LocalDate checkout;
	    private String status;
	    private String image;
	    private String delivery;
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public List<Compartment> getCompartments() {
			return compartments;
		}
		public void setCompartments(List<Compartment> compartments) {
			this.compartments = compartments;
		}
		public Booking getBooking() {
			return booking;
		}
		public void setBooking(Booking booking) {
			this.booking = booking;
		}
		public LocalDate getCheckin() {
			return checkin;
		}
		public void setCheckin(LocalDate checkin) {
			this.checkin = checkin;
		}
		public LocalDate getCheckout() {
			return checkout;
		}
		public void setCheckout(LocalDate checkout) {
			this.checkout = checkout;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getDelivery() {
			return delivery;
		}
		public void setDelivery(String delivery) {
			this.delivery = delivery;
		}
		public ItemResponse(String itemId, String name, int quantity, float weight, String type,
				List<Compartment> compartments, Booking booking, LocalDate checkin, LocalDate checkout, String status,
				String image, String delivery) {
			super();
			this.itemId = itemId;
			this.name = name;
			this.quantity = quantity;
			this.weight = weight;
			this.type = type;
			this.compartments = compartments;
			this.booking = booking;
			this.checkin = checkin;
			this.checkout = checkout;
			this.status = status;
			this.image = image;
			this.delivery = delivery;
		}

}
