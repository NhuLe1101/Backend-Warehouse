package com.backend.warehouse.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "compartment")
public class Compartment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compId;
   
    @Column(nullable = false)
    private String nameComp;
	
//    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
//    private List<Item> items;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item; // Một Compartment chỉ chứa một loại Item

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posId", referencedColumnName = "posId")
    private Position position;
    
    private int layerIndex;
    
    private String side;
    
    private boolean hasItem;
    
    private long itemQuantity;
    
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
    
    @ManyToOne
    @JoinColumn(name = "shelfId")
    private Shelf shelf;

    public Compartment() {
    	
    }
//	public Compartment(String nameComp, List<Item> items, Position position, int layerIndex, String side,
//			boolean hasItem, Booking booking, Shelf shelf) {
//		super();
//		this.nameComp = nameComp;
//		this.items = items;
//		this.position = position;
//		this.layerIndex = layerIndex;
//		this.side = side;
//		this.hasItem = hasItem;
//		this.booking = booking;
//		this.shelf = shelf;
//	}
    

	public Compartment(String nameComp, Item item, Position position, int layerIndex, String side, boolean hasItem,
			int itemQuantity, Booking booking, Shelf shelf) {
		super();
		this.nameComp = nameComp;
		this.item = item;
		this.position = position;
		this.layerIndex = layerIndex;
		this.side = side;
		this.hasItem = hasItem;
		this.itemQuantity = itemQuantity;
		this.booking = booking;
		this.shelf = shelf;
	}
    
    
	public Long getCompId() {
		return compId;
	}
	
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public String getNameComp() {
		return nameComp;
	}
	public void setNameComp(String nameComp) {
		this.nameComp = nameComp;
	}
//	public List<Item> getItems() {
//		return items;
//	}
//	public void setItems(List<Item> items) {
//		this.items = items;
//	}
	
	
	public Position getPosition() {
		return position;
	}
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	public int getLayerIndex() {
		return layerIndex;
	}
	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public boolean isHasItem() {
		return hasItem;
	}
	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
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

	public long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	
    
}
