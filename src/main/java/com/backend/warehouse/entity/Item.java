package com.backend.warehouse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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

<<<<<<< HEAD
	@ManyToOne
	@JoinColumn(name = "compId")
	private Compartment compartment; // compartment mà Product thuộc về
=======
//	@ManyToOne
//	@JoinColumn(name = "comId")
//	private Compartment compartment; // Compartment mà Product thuộc về
	
	@OneToMany
	@JoinColumn(name = "comId")
	private List<Compartment> compartments; // Compartment(s) that Product belongs to

>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71

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
	
    @Column(nullable = true , length = 1000)
    private String image;
    
	@Column(nullable = true)
    private String delivery;

	public Item() {

	}

<<<<<<< HEAD

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
=======
//	public Item(String name, int quantity, float weight, String type, Compartment compartment, Shelf shelf, Booking booking,
//			LocalDate checkin, LocalDate checkout, String status, String image, String delivery) {
//		super();
//		this.name = name;
//		this.quantity = quantity;
//		this.weight = weight;
//		this.type = type;
//		this.compartment = compartment;
//		this.shelf = shelf;
//		this.booking = booking;
//		this.checkin = checkin;
//		this.checkout = checkout;
//		this.status = status;
//		this.image = image;
//		this.delivery = delivery;
//	}
	
	
>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71


	public Long getItemId() {
		return itemId;
	}

<<<<<<< HEAD
=======
	public Item(String name, int quantity, float weight, String type, List<Compartment> compartments, Shelf shelf,
		Booking booking, LocalDate checkin, LocalDate checkout, String status, String image, String delivery) {
	super();
	this.name = name;
	this.quantity = quantity;
	this.weight = weight;
	this.type = type;
	this.compartments = compartments;
	this.shelf = shelf;
	this.booking = booking;
	this.checkin = checkin;
	this.checkout = checkout;
	this.status = status;
	this.image = image;
	this.delivery = delivery;
}
>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71

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

<<<<<<< HEAD

	public Compartment getCompartment() {
		return compartment;
	}


	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}


=======
//	public Compartment getCompartment() {
//		return compartment;
//	}
//
//	public void setCompartment(Compartment compartment) {
//		this.compartment = compartment;
//	}

	public List<Compartment> getCompartments() {
		return compartments;
	}

	public void setCompartments(List<Compartment> compartments) {
		this.compartments = compartments;
	}	
	
>>>>>>> 7b0c69fb58efb5ea36755c34c9ad768737707b71
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
