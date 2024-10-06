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
import jakarta.persistence.Table;

@Entity
@Table(name = "compartment")
public class Compartment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long compId;

	@Column(nullable = false)
	private String nameComp;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(nullable = false) // Thêm annotation để lưu tầng của ngăn
	private int layerIndex; // Tầng của ngăn

	@Column(nullable = false)
	private int side; // (1: left, 2: mid, 3: right)

	@Column(nullable = false) // Thêm annotation để lưu trạng thái có item hay không
	private boolean hasItem; // Trạng thái có item hay không

	@ManyToOne
	@JoinColumn(name = "shelfId")
	private Shelf shelf;

	public Compartment() {

	}

	public Compartment(String nameComp, Item item, int layerIndex, int side, boolean hasItem, Shelf shelf) {
		super();
		this.nameComp = nameComp;
		this.item = item;
		this.layerIndex = layerIndex;
		this.side = side;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
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
