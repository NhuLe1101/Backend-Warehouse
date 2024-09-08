package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.util.Date;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	private Dimension dimension;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "posId", referencedColumnName = "posId")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "packageId")
	private Package aPackage; // Package mà Product thuộc về

	@ManyToOne
	@JoinColumn(name = "shelfId")
	private Shelf shelf; // Shelf mà Product đang được đặt

	@ManyToOne
	@JoinColumn(name = "bookingId")
	private Booking booking; // Booking mà Product liên quan

	public Item() {

	}

	public Item(String name, int quantity, float weight, String type, Dimension dimension, Position position,
			Package aPackage, Shelf shelf, Booking booking) {
		this.name = name;
		this.quantity = quantity;
		this.weight = weight;
		this.type = type;
		this.dimension = dimension;
		this.position = position;
		this.aPackage = aPackage;
		this.shelf = shelf;
		this.booking = booking;
	}

	// Method to get check-in and check-out dates from Booking
	public Date getCheckIn() {
		return booking != null ? booking.getCheckIn() : null;
	}

	public Date getCheckOut() {
		return booking != null ? booking.getCheckOut() : null;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Package getaPackage() {
		return aPackage;
	}

	public void setaPackage(Package aPackage) {
		this.aPackage = aPackage;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

}
