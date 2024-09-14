package com.backend.warehouse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "package")
public class Package {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    @Column(nullable = false)
    private float weight;

    @Column(nullable = true)
    private String type; // Loại của Package, tương ứng với loại của Item
    
    @OneToMany(mappedBy = "aPackage", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dimId", referencedColumnName = "dimId")
    private Dimension dimension;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posId", referencedColumnName = "posId")
    private Position position;
    
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
    
    @ManyToOne
    @JoinColumn(name = "shelfId")
    private Shelf shelf;

    public Package() {
    	
    }

	public Package(float weight, String type, List<Item> items, Dimension dimension, Position position, Booking booking,
			Shelf shelf) {
		this.weight = weight;
		this.type = type;
		this.items = items;
		this.dimension = dimension;
		this.position = position;
		this.booking = booking;
		this.shelf = shelf;
	}

	public LocalDate getCheckInFromItems() {
        return items != null && !items.isEmpty() ? items.get(0).getCheckIn() : null;
    }

    // Phương thức để lấy check-out từ các items
    public LocalDate getCheckOutFromItems() {
        return items != null && !items.isEmpty() ? items.get(0).getCheckOut() : null;
    }

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
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

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
