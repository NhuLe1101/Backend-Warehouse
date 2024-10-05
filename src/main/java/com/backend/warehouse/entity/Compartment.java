package com.backend.warehouse.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> items;
    
    @Column(nullable = false)  // Thêm annotation để lưu tầng của ngăn
    private int layerIndex;  // Tầng của ngăn

    @Column(nullable = false)  // Thêm annotation để lưu trạng thái có item hay không
    private boolean hasItem; // Trạng thái có item hay không
    
    @ManyToOne
    @JoinColumn(name = "shelfId")
    private Shelf shelf;

    public Compartment() {
    	
    }
	
	public Compartment(String nameComp, List<Item> items, int layerIndex, boolean hasItem,
			Shelf shelf) {
		this.nameComp = nameComp;
		this.items = items;
		this.layerIndex = layerIndex;
		this.hasItem = hasItem;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

	public boolean isHasItem() {
		return hasItem;
	}

	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	
    
}
