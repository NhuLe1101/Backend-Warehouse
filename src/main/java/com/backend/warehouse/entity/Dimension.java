package com.backend.warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dimension")
public class Dimension {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dimId;

	@Column(nullable = false)
    private float xCoord;
    
	@Column(nullable = false)
    private float yCoord;
	
	@Column(nullable = false)
    private float zCoord;

	public Dimension() {

	}
	
	public Dimension(float xCoord, float yCoord, float zCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
	}
	

	public Long getDimId() {
		return dimId;
	}

	public void setDimId(Long dimId) {
		this.dimId = dimId;
	}
	public float getxCoord() {
		return xCoord;
	}

	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}

	public float getyCoord() {
		return yCoord;
	}

	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}

	public float getzCoord() {
		return zCoord;
	}

	public void setzCoord(float zCoord) {
		this.zCoord = zCoord;
	}
	
	
}