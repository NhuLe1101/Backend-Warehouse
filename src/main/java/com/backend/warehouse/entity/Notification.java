package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;
	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	private NotificationType type; // Enum to distinguish types like CHECKOUT_REMINDER, ITEM_CHECKOUT
	
	@ManyToOne
	@JoinColumn(name = "item_id", nullable = true)
	private Item item;

	@ManyToOne
	@JoinColumn(name = "shelf_id", nullable = true)
	private Shelf shelf;

	@ManyToOne
	@JoinColumn(name = "compartment_id", nullable = true)
	private Compartment compartment;

	public Notification() {

	}
	
	public Notification(String message, LocalDateTime timestamp, NotificationType type, Item item, Shelf shelf,
			Compartment compartment) {
		this.message = message;
		this.timestamp = timestamp;
		this.type = type;
		this.item = item;
		this.shelf = shelf;
		this.compartment = compartment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public Compartment getCompartment() {
		return compartment;
	}

	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}

}
