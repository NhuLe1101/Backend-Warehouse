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
    private String nameShelf;
    
    @Column(nullable = false)
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posId", referencedColumnName = "posId")
    private Position position;

    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
    private List<Compartment> compartments;

    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    public Shelf() {
    	
    }

	public Shelf(String nameShelf, String type, Position position, List<Compartment> compartments,
			Warehouse warehouse) {
		super();
		this.nameShelf = nameShelf;
		this.type = type;
		this.position = position;
		this.compartments = compartments;
		this.warehouse = warehouse;
	}

	public Long getShelfId() {
		return shelfId;
	}

	public void setShelfId(Long shelfId) {
		this.shelfId = shelfId;
	}

	public String getNameShelf() {
		return nameShelf;
	}

	public void setNameShelf(String nameShelf) {
		this.nameShelf = nameShelf;
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

	public List<Compartment> getCompartments() {
		return compartments;
	}

	public void setCompartments(List<Compartment> compartments) {
		this.compartments = compartments;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
    

}
