package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shelf")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelfId;
    
    @Column(nullable = false)
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posId", referencedColumnName = "posId")
    private Position position;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<Package> packages;

    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    public Shelf() {
    	
    }
    
	public Shelf(String type, Position position, List<Package> packages, Warehouse warehouse) {
		this.type = type;
		this.position = position;
		this.packages = packages;
		this.warehouse = warehouse;
	}

	public Long getShelfId() {
		return shelfId;
	}

	public void setShelfId(Long shelfId) {
		this.shelfId = shelfId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
    
}
