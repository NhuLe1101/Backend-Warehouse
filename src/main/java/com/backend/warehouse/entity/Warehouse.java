package com.backend.warehouse.entity;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "warehouseinfo")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<Shelf> shelves;

    public Warehouse() {
    	
    }
    
	public Warehouse(String name, String location, List<Shelf> shelves) {
		this.name = name;
		this.location = location;
		this.shelves = shelves;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Shelf> getShelves() {
		return shelves;
	}

	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}
    
    
}
