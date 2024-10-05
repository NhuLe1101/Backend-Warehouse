package com.backend.warehouse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = true)
	private float weight;

	@Column(nullable = true)
	private String type;

	@ManyToOne
	@JoinColumn(name = "compId")
	private Compartment compartment; // compartment mà Product thuộc về

	@ManyToOne
	@JoinColumn(name = "shelfId")
	private Shelf shelf; // Shelf mà Product đang được đặt

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking; // Booking mà Product liên quan
	
	@Column(nullable = false)
    private LocalDate checkin;
	
	@Column(nullable = false)
    private LocalDate checkout;
	
	@Column(nullable = false)
    private String status;
	
    @Column(length = 1000)
    private String image;
    
	@Column(nullable = false)
    private String delivery;

	public Item() {

	}


	public Item(String name, int quantity, float weight, String type, Compartment compartment, Shelf shelf,
			Booking booking, LocalDate checkin, LocalDate checkout, String status, String image, String delivery) {
		this.name = name;
		this.quantity = quantity;
		this.weight = weight;
		this.type = type;
		this.compartment = compartment;
		this.shelf = shelf;
		this.booking = booking;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
		this.image = image;
		this.delivery = delivery;
	}


	public Long getItemId() {
		return itemId;
	}


	public void setItemId(Long itemId) {
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


	public Compartment getCompartment() {
		return compartment;
	}


	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}


	public Shelf getShelf() {
		return shelf;
	}


	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
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



}
